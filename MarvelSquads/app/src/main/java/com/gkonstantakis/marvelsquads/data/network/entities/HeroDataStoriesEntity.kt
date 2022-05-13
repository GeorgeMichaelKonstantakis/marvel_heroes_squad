package com.gkonstantakis.marvelsquads.data.network.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HeroDataStoriesEntity(
    @SerializedName("available")
    @Expose
    var available: Int,

    @SerializedName("returned")
    @Expose
    var returned: Int,

    @SerializedName("collectionURI")
    @Expose
    var collectionURI: String,

    @SerializedName("items")
    @Expose
    var items: List<HeroDataStoriesItemsEntity>
)