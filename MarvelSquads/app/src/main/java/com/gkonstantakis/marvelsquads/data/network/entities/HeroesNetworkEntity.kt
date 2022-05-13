package com.gkonstantakis.marvelsquads.data.network.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class HeroesNetworkEntity(
    @SerializedName("code")
    @Expose
    var code: Int,

    @SerializedName("status")
    @Expose
    var status: String,

    @SerializedName("copyright")
    @Expose
    var copyright: String,

    @SerializedName("attributionText")
    @Expose
    var attributionText: String,

    @SerializedName("attributionHTML")
    @Expose
    var attributionHTML: String,

    @SerializedName("data")
    @Expose
    var data: HeroesDataEntity,

    @SerializedName("etag")
    @Expose
    var etag: String
) {
}