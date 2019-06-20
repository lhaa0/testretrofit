package com.rizki.testretrofitrizki.service

import com.rizki.testretrofitrizki.model.ModelMovie.ModelMovie
import com.rizki.testretrofitrizki.model.ResponseMovie.ResponseMovie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("movie/latest")
    fun getMovieLatest(@Query("api_key") apiKey : String) : Call<ModelMovie>

    @GET("movie/popular")
    fun getPopularMovie(@Query("api_key") apiKey: String) : Call<ResponseMovie>

    @GET("movie/{id}")
    fun getMovieById(@Path("id") movieId: Int,
                     @Query("api_key") apiKey: String) : Call<ModelMovie>
}