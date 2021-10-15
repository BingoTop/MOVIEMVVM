package com.james.movietmdb.data.model.detail.detail_movie


import com.google.gson.annotations.SerializedName

data class ProductionCompany(
    @SerializedName("id")
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String
)