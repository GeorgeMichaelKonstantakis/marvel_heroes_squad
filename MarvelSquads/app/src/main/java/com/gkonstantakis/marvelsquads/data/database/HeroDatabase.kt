package com.gkonstantakis.marvelsquads.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gkonstantakis.marvelsquads.data.database.entities.HeroCacheEntity
import com.gkonstantakis.marvelsquads.data.database.entities.HeroSquadEntity

@Database(entities = [HeroCacheEntity::class, HeroSquadEntity::class], version = 1)
abstract class HeroDatabase : RoomDatabase() {

    abstract fun heroDao(): HeroDao

    companion object {
        const val HERO_DATABASE: String = "hero_db"
    }
}