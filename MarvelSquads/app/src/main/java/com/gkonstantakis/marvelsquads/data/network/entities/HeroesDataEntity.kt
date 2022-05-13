package com.gkonstantakis.marvelsquads.data.network.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HeroesDataEntity (

    @SerializedName("offset")
    @Expose
    var offset: Int,

    @SerializedName("limit")
    @Expose
    var limit: Int,

    @SerializedName("total")
    @Expose
    var total: Int,

    @SerializedName("count")
    @Expose
    var count: Int,

    @SerializedName("results")
    @Expose
    var results: List<HeroDataEntity>

)