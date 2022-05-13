package com.gkonstantakis.marvelsquads.data.network.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HeroDataUrlsEntity(
    @SerializedName("type")
    @Expose
    var type: String,

    @SerializedName("url")
    @Expose
    var url: String

)