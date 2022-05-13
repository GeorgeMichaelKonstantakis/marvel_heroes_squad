package com.gkonstantakis.marvelsquads.ui.view_models

import androidx.lifecycle.*
import com.gkonstantakis.marvelsquads.data.repository.MainRepository
import com.gkonstantakis.marvelsquads.data.model.Hero
import com.gkonstantakis.marvelsquads.data.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainRepository: MainRepository?
): ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<Hero>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Hero>>>
        get() = _dataState


    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch {
            when(mainStateEvent) {
                is MainStateEvent.GetHeroEvents -> {
                    mainRepository!!.getHero()!!.onEach { dataState ->
                        _dataState.value = dataState
                    }!!.launchIn(viewModelScope)
                }
                is MainStateEvent.GetSquadEvents -> {
                    mainRepository!!.getSquad()!!.onEach { dataState ->
                        _dataState.value = dataState
                    }!!.launchIn(viewModelScope)
                }
            }
        }
    }
}

sealed class MainStateEvent {
    object GetHeroEvents: MainStateEvent()

    object GetSquadEvents: MainStateEvent()

    object None: MainStateEvent()
}