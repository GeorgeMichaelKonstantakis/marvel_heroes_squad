package com.gkonstantakis.marvelsquads.data.network.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class HeroDataEntity(

    @SerializedName("id")
    @Expose
    var id: Int,

    @SerializedName("name")
    @Expose
    var name: String,

    @SerializedName("description")
    @Expose
    var description: String,

    @SerializedName("modified")
    @Expose
    var modified: Date,

    @SerializedName("resourceURI")
    @Expose
    var resourceURI: String,

    @SerializedName("urls")
    @Expose
    var urls: List<HeroDataUrlsEntity>,

    @SerializedName("thumbnail")
    @Expose
    var thumbnail: HeroDataThumbnailEntity,

    @SerializedName("comics")
    @Expose
    var comics: HeroDataComicsEntity,

    @SerializedName("stories")
    @Expose
    var stories: HeroDataStoriesEntity,

    @SerializedName("events")
    @Expose
    var events: HeroDataEventsEntity,

    @SerializedName("series")
    @Expose
    var series: HeroDataSeriesEntity
)