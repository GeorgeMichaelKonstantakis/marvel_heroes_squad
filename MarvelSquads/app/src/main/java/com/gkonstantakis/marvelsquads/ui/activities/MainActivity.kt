package com.gkonstantakis.marvelsquads.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkonstantakis.marvelsquads.MarvelSquadsApplication
import com.gkonstantakis.marvelsquads.R
import com.gkonstantakis.marvelsquads.data.repository.MainRepository
import com.gkonstantakis.marvelsquads.data.database.HeroDao
import com.gkonstantakis.marvelsquads.data.database.HeroDatabase
import com.gkonstantakis.marvelsquads.data.model.Hero
import com.gkonstantakis.marvelsquads.data.network.HeroNetworkService
import com.gkonstantakis.marvelsquads.data.utils.DataState
import com.gkonstantakis.marvelsquads.ui.view_models.MainStateEvent
import com.gkonstantakis.marvelsquads.ui.view_models.MainViewModel
import com.gkonstantakis.marvelsquads.ui.adapters.HeroesListAdapter
import com.gkonstantakis.marvelsquads.ui.adapters.HeroesSquadAdapter
import com.gkonstantakis.marvelsquads.ui.mapping.HeroesListMapper
import com.gkonstantakis.marvelsquads.ui.mapping.HeroesSquadMapper

class MainActivity : AppCompatActivity() {

    private var viewModel: MainViewModel? = null

    private var heroDB: HeroDatabase? = null
    private var heroDao: HeroDao? = null
    private var heroNetworkService: HeroNetworkService? = null
    private var mainRepository: MainRepository? = null

    private var squadHeroesTitle: TextView? = null
    private var progressBar: ProgressBar? = null

    private lateinit var heroesListRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        heroDB = (application as MarvelSquadsApplication).heroDB
        heroDao = (application as MarvelSquadsApplication).heroDao
        heroNetworkService = (application as MarvelSquadsApplication).heroNetworkService
        mainRepository = (application as MarvelSquadsApplication).mainRepository

        progressBar = findViewById(R.id.progress_bar)

        viewModel = MainViewModel(mainRepository)
        subscribeObservers(this)
        viewModel!!.setStateEvent(MainStateEvent.GetHeroEvents)
    }

    private fun subscribeObservers(context: Context) {
        viewModel?.dataState?.observe(this, Observer { datastate ->
            when (datastate) {
                is DataState.SuccessNetwork<List<Hero>> -> {
                    displayHeroesList(context, datastate.data)
                }
                is DataState.SuccessDatabaseHeroList<List<Hero>> -> {
                    displayHeroesList(context, datastate.data)
                }
                is DataState.SuccessDatabaseSquadList<List<Hero>> -> {
                    displaySquadList(context, datastate.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    fun displayHeroesList(context: Context, data: List<Hero>) {
        val heroListAdapter = HeroesListAdapter(HeroesListMapper().mapFromEntityList(data), mainRepository!!)
        val layoutManager: LinearLayoutManager? = LinearLayoutManager(this);
        heroesListRecyclerView = this.findViewById(R.id.heroes_list)
        layoutManager?.orientation = LinearLayoutManager.VERTICAL;
        heroesListRecyclerView.layoutManager = layoutManager
        heroesListRecyclerView.adapter = heroListAdapter
        viewModel!!.setStateEvent(MainStateEvent.GetSquadEvents)
        displayProgressBar(false)
    }

    fun displaySquadList(context: Context, data: List<Hero>) {
        if (!data.isNullOrEmpty()) {
            squadHeroesTitle = findViewById<TextView>(R.id.squad_title)
            squadHeroesTitle?.visibility = View.VISIBLE
        }
        val heroSquadAdapter = HeroesSquadAdapter(HeroesSquadMapper().mapFromEntityList(data), context)
        val layoutManager: LinearLayoutManager? = LinearLayoutManager(this);
        val heroSquadRecyclerView: RecyclerView = this.findViewById(R.id.hereos_squad)
        layoutManager?.orientation = LinearLayoutManager.HORIZONTAL;
        heroSquadRecyclerView.layoutManager = layoutManager
        heroSquadRecyclerView.adapter = heroSquadAdapter
        displayProgressBar(false)
    }

    fun displayProgressBar(display: Boolean) {
        if (display) {
            progressBar?.visibility = View.VISIBLE
        } else {
            progressBar?.visibility = View.GONE
        }
    }
}
