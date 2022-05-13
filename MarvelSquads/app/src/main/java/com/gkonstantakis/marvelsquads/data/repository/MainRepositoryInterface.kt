package com.gkonstantakis.marvelsquads.data.repository

import com.gkonstantakis.marvelsquads.data.database.entities.HeroSquadEntity
import com.gkonstantakis.marvelsquads.data.model.Hero
import com.gkonstantakis.marvelsquads.data.utils.DataState
import kotlinx.coroutines.flow.Flow

interface MainRepositoryInterface {

    suspend fun getHero(): Flow<DataState<List<Hero>>>

    suspend fun getSquad(): Flow<DataState<List<Hero>>>

    suspend fun hireHero(squadHero: HeroSquadEntity): Flow<DataState<List<HeroSquadEntity>>>

    suspend fun fireHero(squadHero: HeroSquadEntity): Flow<DataState<List<HeroSquadEntity>>>

    suspend fun isHeroInSquad(id: Int): Boolean
}