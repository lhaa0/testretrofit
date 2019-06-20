package com.rizki.testretrofitrizki.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {

        var retrofit: Retrofit? = null

        fun getClient() : Retrofit {
            if (retrofit == null) {
                val BASE_URL = "https://api.themoviedb.org/3/"
                retrofit =
                    Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit!!
        }
    }
}