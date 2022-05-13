package com.gkonstantakis.marvelsquads.data.network

import com.gkonstantakis.marvelsquads.data.network.entities.HeroesNetworkEntity
import com.gkonstantakis.marvelsquads.data.utils.Constant
import retrofit2.http.GET
import retrofit2.http.Query

interface HeroNetworkService {

    @GET("characters")
    suspend fun get(
        @Query("ts") ts: String = Constant.ts,
        @Query("apikey") apikey: String = Constant.API_KEY,
        @Query("hash") hash: String = Constant.computeHash()
    ): HeroesNetworkEntity
}