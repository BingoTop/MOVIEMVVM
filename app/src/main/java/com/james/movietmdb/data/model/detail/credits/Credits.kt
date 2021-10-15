package com.james.movietmdb.data.model.detail.credits


import com.google.gson.annotations.SerializedName

data class Credits(
    @SerializedName("cast")
    val castDto: List<CastDto>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)




