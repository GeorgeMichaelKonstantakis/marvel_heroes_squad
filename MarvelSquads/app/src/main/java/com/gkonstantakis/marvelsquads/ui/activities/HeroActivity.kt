package com.gkonstantakis.marvelsquads.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.gkonstantakis.marvelsquads.MarvelSquadsApplication
import com.gkonstantakis.marvelsquads.R
import com.gkonstantakis.marvelsquads.data.repository.MainRepository
import com.gkonstantakis.marvelsquads.data.database.HeroDao
import com.gkonstantakis.marvelsquads.data.database.HeroDatabase
import com.gkonstantakis.marvelsquads.data.database.entities.HeroSquadEntity
import com.gkonstantakis.marvelsquads.data.mapping.DatabaseMapper
import com.gkonstantakis.marvelsquads.data.model.Hero
import com.gkonstantakis.marvelsquads.data.network.HeroNetworkService
import com.gkonstantakis.marvelsquads.data.utils.DataState
import com.gkonstantakis.marvelsquads.ui.view_models.HeroStateEvent
import com.gkonstantakis.marvelsquads.ui.view_models.HeroViewModel
import com.gkonstantakis.marvelsquads.ui.intents.Intents
import com.gkonstantakis.marvelsquads.ui.utils.Utils

class HeroActivity : AppCompatActivity() {

    private var viewModel: HeroViewModel? = null

    private var heroDB: HeroDatabase? = null
    private var heroDao: HeroDao? = null
    private var heroNetworkService: HeroNetworkService? = null
    private var mainRepository: MainRepository? = null

    private var isHeroInSquad = false

    private var heroButton: Button? = null
    private var dismissHeroButton: Button? = null
    private var heroTitle: TextView? = null
    private var heroDescription: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero)

        heroDB = (application as MarvelSquadsApplication).heroDB
        heroDao = (application as MarvelSquadsApplication).heroDao
        heroNetworkService = (application as MarvelSquadsApplication).heroNetworkService
        mainRepository = (application as MarvelSquadsApplication).mainRepository

        viewModel = HeroViewModel(mainRepository)

        val hero = Hero(
            intent.getIntExtra("HERO_ID", 0),
            intent.getStringExtra("HERO_NAME").toString(),
            intent.getStringExtra("HERO_DESCRIPTION").toString(),
            intent.getStringExtra("HERO_IMAGE_PATH").toString()
        )

        isHeroInSquad = intent.getBooleanExtra("IS_HERO_IN_SQUAD", false)

        val heroUrl = hero.imageUrl
        var heroAcceptedUrl = Utils().safeUrl(heroUrl)

        heroTitle = findViewById(R.id.hero_title)
        heroTitle?.text = hero.name

        heroDescription = findViewById(R.id.hero_description)
        heroDescription?.text = hero.description

        Glide.with(this).load(heroAcceptedUrl)
            .into(this.findViewById<ImageView>(R.id.hero_thumbnail_image));

        dismissHeroButton = findViewById<Button>(R.id.dismiss_hero)
        dismissHeroButton?.setOnClickListener {
            Intents().mainActivityIntent(this)
        }


        heroButton = findViewById<Button>(R.id.hero_button)
        buttonStyling()
        heroButton?.setOnClickListener {
            if (isHeroInSquad) {
                viewModel!!.setStateEvent(
                    HeroStateEvent.FireHeroEvent,
                    DatabaseMapper().mapToHeroSquadEntity(hero)
                )
            } else {
                viewModel!!.setStateEvent(
                    HeroStateEvent.HireHeroEvent,
                    DatabaseMapper().mapToHeroSquadEntity(hero)
                )
            }
        }

        subscribeObservers(this)
        viewModel!!.setStateEvent(
            HeroStateEvent.GetHeroEvent,
            DatabaseMapper().mapToHeroSquadEntity(hero)
        )
    }

    private fun subscribeObservers(context: Context) {
        viewModel?.dataState?.observe(this, Observer { datastate ->
            when (datastate) {
                is DataState.SuccessHireOrFireHero<List<HeroSquadEntity>> -> {
                    Intents().mainActivityIntent(this)
                }
                is DataState.Error -> {

                }
                is DataState.Loading -> {

                }
            }
        })
    }

    fun buttonStyling() {
        if (isHeroInSquad) {
            heroButton?.text = resources.getString(R.string.fire_from_squad)
            heroButton?.background = resources.getDrawable(R.drawable.red_button)
        } else {
            heroButton?.text = resources.getString(R.string.hire_to_squad)
        }
    }
}