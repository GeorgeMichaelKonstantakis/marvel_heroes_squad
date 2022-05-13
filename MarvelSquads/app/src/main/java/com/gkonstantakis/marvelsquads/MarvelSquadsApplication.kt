package com.gkonstantakis.marvelsquads

import android.app.Application
import androidx.room.Room
import com.gkonstantakis.marvelsquads.data.repository.MainRepository
import com.gkonstantakis.marvelsquads.data.database.HeroDao
import com.gkonstantakis.marvelsquads.data.database.HeroDatabase
import com.gkonstantakis.marvelsquads.data.mapping.DatabaseMapper
import com.gkonstantakis.marvelsquads.data.mapping.NetworkMapper
import com.gkonstantakis.marvelsquads.data.network.HeroNetworkService
import com.gkonstantakis.marvelsquads.data.utils.Constant
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MarvelSquadsApplication: Application() {

    lateinit var heroDB: HeroDatabase
    lateinit var heroDao: HeroDao
    lateinit var heroNetworkService: HeroNetworkService
    lateinit var mainRepository: MainRepository

    override fun onCreate() {
        super.onCreate()
        heroDB = Room
            .databaseBuilder(
                applicationContext,
                HeroDatabase::class.java,
                HeroDatabase.HERO_DATABASE
            )
            .build()
        heroDao = (heroDB as HeroDatabase).heroDao()
        heroNetworkService =
            provideGsonBuilder()!!.let {
                provideNetwork(it)!!.build()!!.create(HeroNetworkService::class.java)
            }
        mainRepository =
            heroNetworkService!!.let {
                MainRepository(
                    heroDao!!,
                    it, DatabaseMapper(), NetworkMapper()
                )
            }
    }

    fun provideGsonBuilder(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    fun provideNetwork(gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }
}