package com.gkonstantakis.marvelsquads.ui.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gkonstantakis.marvelsquads.data.repository.MainRepository
import com.gkonstantakis.marvelsquads.data.database.entities.HeroSquadEntity
import com.gkonstantakis.marvelsquads.data.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HeroViewModel(
    private val mainRepository: MainRepository?
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<HeroSquadEntity>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<HeroSquadEntity>>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: HeroStateEvent, heroSquadEntity: HeroSquadEntity) {
        viewModelScope.launch {
            when (mainStateEvent) {
                is HeroStateEvent.GetHeroEvent -> {

                }
                is HeroStateEvent.HireHeroEvent -> {
                    mainRepository!!.hireHero(heroSquadEntity)!!.onEach { dataState ->
                        _dataState.value = dataState
                    }!!.launchIn(viewModelScope)
                }
                is HeroStateEvent.FireHeroEvent -> {
                    mainRepository!!.fireHero(heroSquadEntity)!!.onEach { dataState ->
                        _dataState.value = dataState
                    }!!.launchIn(viewModelScope)
                }
            }
        }
    }
}

sealed class HeroStateEvent {
    object GetHeroEvent: HeroStateEvent()

    object HireHeroEvent : HeroStateEvent()

    object FireHeroEvent : HeroStateEvent()

    object None : HeroStateEvent()
}