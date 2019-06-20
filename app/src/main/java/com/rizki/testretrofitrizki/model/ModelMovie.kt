package com.rizki.testretrofitrizki.model

import com.google.gson.annotations.SerializedName

object ModelMovie {
    data class ModelMovie(
        @SerializedName("id") val id: Int?,
        @SerializedName("original_title") val originalTitle: String?,
        @SerializedName("overview") val overview: String?,
        @SerializedName("poster_path") val posterPath: String?,
        @SerializedName("genres") val genres: ArrayList<Genre>?,
        @SerializedName("vote_average") val vote_average: Float?,
        @SerializedName("release_date") val release_date: String?,
        @SerializedName("adult") val adult: Boolean?
    )

    data class Genre(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String
    )

}