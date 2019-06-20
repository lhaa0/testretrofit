package com.rizki.testretrofitrizki

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.rizki.testretrofitrizki.model.ModelMovie.ModelMovie
import com.rizki.testretrofitrizki.service.ApiClient
import com.rizki.testretrofitrizki.service.ApiInterface
import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.movie_desc.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        setSupportActionBar(toolbar)
        getMovieData()
    }

    private fun getMovieData(){
        val apiInterface : ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call : Call<ModelMovie> = apiInterface.getMovieById(intent.getIntExtra("movieid", 0) ,resources.getString(R.string.api_key))
        call.enqueue(object : Callback<ModelMovie> {
            override fun onFailure(call: Call<ModelMovie>, t: Throwable) {
                Log.d("TAG", "Gagal Fetch Popular Movie")
            }

            override fun onResponse(call: Call<ModelMovie>, response: Response<ModelMovie>) {
                val originalTitle : String? = response.body()?.originalTitle
                val posterPath : String? = response.body()?.posterPath

                collapseToolbar.title = originalTitle
                if (posterPath == null) {
                    collapseImage.setImageResource(R.drawable.ic_launcher_background)
                } else {
                    val imageUrl = StringBuilder()
                    imageUrl.append(getString(R.string.base_path_poster)).append(posterPath)
                    Glide.with(applicationContext).load(imageUrl.toString()).into(collapseImage)
                }
                release_date.text = response.body()?.release_date
                overview.text = response.body()?.overview
                vote_average.rating = (response.body()?.vote_average!! / 2)
                if (response.body()?.adult!!)
                    adult.text = "YES"
                else adult.text = "NO"
                val genres : ArrayList<com.rizki.testretrofitrizki.model.ModelMovie.Genre> = response.body()?.genres!!
                val sgenre = StringBuilder()
                for (genre in genres){
                    sgenre.append(genre.name + " ")
                }
                genre.text = sgenre.toString().trim()
            }
        })
    }
}
