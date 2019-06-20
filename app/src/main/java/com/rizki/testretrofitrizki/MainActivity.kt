package com.rizki.testretrofitrizki

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.rizki.testretrofitrizki.adapter.MovieAdapter
import com.rizki.testretrofitrizki.model.ModelMovie.ModelMovie
import com.rizki.testretrofitrizki.model.ResponseMovie.ResponseMovie
import com.rizki.testretrofitrizki.service.ApiClient
import com.rizki.testretrofitrizki.service.ApiInterface
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movie_desc.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var movies : ArrayList<ModelMovie>
    var desc = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        rvMovies.layoutManager = GridLayoutManager(applicationContext, 2)

        val apiKey = getString(R.string.api_key)
        val apiInterface : ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        getLatestMovie(apiInterface, apiKey)
        getPopularMovies(apiInterface, apiKey)


    }

    private fun getPopularMovies(apiInterface: ApiInterface, apiKey : String) {
        val call : Call<ResponseMovie> = apiInterface.getPopularMovie(apiKey)
        call.enqueue(object : Callback<ResponseMovie> {
            override fun onFailure(call: Call<ResponseMovie>?, t: Throwable?) {
                Log.d("TAG", "Gagal Fetch Popular Movie")
            }

            override fun onResponse(call: Call<ResponseMovie>?, response: Response<ResponseMovie>?) {
                movies = response!!.body()!!.results
                Log.d("TAG", "Movie size ${movies.size}")
                rvMovies.adapter = MovieAdapter(movies)
            }

        })
    }

    private fun getLatestMovie(apiInterface: ApiInterface, apiKey : String) : ModelMovie? {
        val movie : ModelMovie? = null
        val call : Call<ModelMovie> = apiInterface.getMovieLatest(apiKey)
        call.enqueue(object : Callback<ModelMovie> {
            override fun onFailure(call: Call<ModelMovie>?, t: Throwable?) {
                Log.d("TAG", "Gagal Fetch Popular Movie")
            }

            override fun onResponse(call: Call<ModelMovie>?, response: Response<ModelMovie>?) {
                if (response != null) {
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
                    collapseImage.setOnClickListener {
                        if (desc) {
                            descView.visibility = View.GONE
                            rvMovies.visibility = View.VISIBLE
                            desc = false
                        } else {
                            descView.visibility = View.VISIBLE
                            rvMovies.visibility = View.GONE
                            desc = true
                        }
                    }
                    descView.visibility = View.GONE
                }
            }

        })

        return movie
    }

}
