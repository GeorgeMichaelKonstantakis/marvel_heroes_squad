package com.gkonstantakis.marvelsquads.data.network.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HeroDataStoriesItemsEntity(
    @SerializedName("resourceURI")
    @Expose
    var resourceURI: String,

    @SerializedName("name")
    @Expose
    var name: String,

    @SerializedName("type")
    @Expose
    var type: String
)