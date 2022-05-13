package com.gkonstantakis.marvelsquads.data.network.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HeroDataThumbnailEntity(
    @SerializedName("path")
    @Expose
    var path: String,

    @SerializedName("extension")
    @Expose
    var extension: String
)