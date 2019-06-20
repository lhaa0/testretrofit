package com.rizki.testretrofitrizki.model

object ResponseMovie {
    data class ResponseMovie(var page : Int,
                             val results : ArrayList<ModelMovie.ModelMovie>,
                             val totalResult : Int,
                             val totalPage : Int)
}