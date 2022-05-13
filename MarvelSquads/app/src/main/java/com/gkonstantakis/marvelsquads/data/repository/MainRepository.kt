package com.gkonstantakis.marvelsquads.data.repository

import com.gkonstantakis.marvelsquads.data.database.HeroDao
import com.gkonstantakis.marvelsquads.data.database.entities.HeroSquadEntity
import com.gkonstantakis.marvelsquads.data.mapping.DatabaseMapper
import com.gkonstantakis.marvelsquads.data.mapping.NetworkMapper
import com.gkonstantakis.marvelsquads.data.model.Hero
import com.gkonstantakis.marvelsquads.data.network.HeroNetworkService
import com.gkonstantakis.marvelsquads.data.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository(
    private val heroDao: HeroDao,
    private val heroNetworkService: HeroNetworkService,
    private val databaseMapper: DatabaseMapper,
    private val networkMapper: NetworkMapper
) : MainRepositoryInterface {

    override suspend fun getHero(): Flow<DataState<List<Hero>>> = flow {
        emit(DataState.Loading)
        try {
            val networkHeroes = heroNetworkService.get()
            if (!networkHeroes.data.results.isNullOrEmpty()) {
                heroDao.deleteHeroes()
                val heroes = networkMapper.mapFromEntityList(networkHeroes)
                for (hero in heroes) {
                    heroDao.insertHero(databaseMapper.mapToHeroCacheEntity(hero))
                }
            }
            val cachedHeroes = heroDao.getHeroes()
            emit(
                DataState.SuccessNetwork(
                    databaseMapper.mapFromHeroCacheEntityEntityList(
                        cachedHeroes
                    ).sortedBy { it.name }
                )
            )
        } catch (e: Exception) {
            try {
                val cachedHeroes = heroDao.getHeroes()
                emit(
                    DataState.SuccessDatabaseHeroList(
                        databaseMapper.mapFromHeroCacheEntityEntityList(
                            cachedHeroes
                        ).sortedBy { it.name }
                    )
                )
            } catch (e2: Exception) {
                emit(DataState.Error(e))
            }
        }
    }

    override suspend fun getSquad(): Flow<DataState<List<Hero>>> = flow {
        emit(DataState.Loading)
        try {
            //val squadIds = heroDao.getSquadIds()
            val squadHeroes = heroDao.getSquadFromHeroesList()
            if (squadHeroes.isNotEmpty()) {
                emit(
                    DataState.SuccessDatabaseSquadList(
                        databaseMapper.mapFromHeroCacheEntityEntityList(
                            squadHeroes
                        ).sortedBy { it.name }
                    )
                )
            } else {
                emit(DataState.Error(Exception("User do not have any hero in the squad")))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun hireHero(squadHero: HeroSquadEntity): Flow<DataState<List<HeroSquadEntity>>> =
        flow {
            emit(DataState.Loading)
            try {
                heroDao.insertSquadHero(squadHero)
                val squadsHeroes: ArrayList<HeroSquadEntity> = ArrayList()
                squadsHeroes.add(squadHero)
                emit(DataState.SuccessHireOrFireHero(squadsHeroes))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }

    override suspend fun fireHero(squadHero: HeroSquadEntity): Flow<DataState<List<HeroSquadEntity>>> =
        flow {
            emit(DataState.Loading)
            try {
                heroDao.deleteSquad(squadHero)
                val squadsHeroes: ArrayList<HeroSquadEntity> = ArrayList()
                squadsHeroes.add(squadHero)
                emit(DataState.SuccessHireOrFireHero(squadsHeroes))
            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }

    override suspend fun isHeroInSquad(id: Int): Boolean {
        try {
            val hero = heroDao.findSquadHeroById(id)
            return hero != null
        } catch (e: Exception) {
            return false
        }
    }
}