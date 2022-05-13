package com.gkonstantakis.marvelsquads.data.database

import androidx.room.*
import com.gkonstantakis.marvelsquads.data.database.entities.HeroCacheEntity
import com.gkonstantakis.marvelsquads.data.database.entities.HeroSquadEntity

@Dao
interface HeroDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHero(heroEntity: HeroCacheEntity): Long

    @Query("SELECT * FROM HEROES")
    suspend fun getHeroes(): List<HeroCacheEntity>

    @Query("DELETE FROM HEROES")
    suspend fun deleteHeroes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSquadHero(squadEntity: HeroSquadEntity): Long

    @Query("SELECT * FROM HEROES WHERE id in squad")
    suspend fun getSquadFromHeroesList(): List<HeroCacheEntity>

    @Query("SELECT * FROM SQUAD")
    suspend fun getSquadIds(): List<HeroSquadEntity>

    @Delete
    suspend fun deleteSquad(heroSquadEntity: HeroSquadEntity)

    @Query("SELECT ID FROM SQUAD WHERE ID = :id")
    suspend fun findSquadHeroById(id: Int): HeroSquadEntity?
}