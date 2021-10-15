package com.james.movietmdb.data.model.detail.detail_movie


import com.google.gson.annotations.SerializedName

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso31661: String,
    @SerializedName("name")
    val name: String
)