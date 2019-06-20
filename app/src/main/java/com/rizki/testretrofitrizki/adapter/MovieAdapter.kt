package com.rizki.testretrofitrizki.adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.rizki.testretrofitrizki.MovieActivity
import com.rizki.testretrofitrizki.R
import com.rizki.testretrofitrizki.model.ModelMovie.ModelMovie
import kotlinx.android.synthetic.main.movie_list.view.*

class MovieAdapter(val movies: ArrayList<ModelMovie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies.get(position))
    }

    override fun getItemCount() = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list, parent, false)
        return MovieViewHolder(view)
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var view: View = itemView
        private var movie: ModelMovie? = null

        override fun onClick(p0: View?) {
            val intent = Intent(view.context, MovieActivity::class.java)
            intent.putExtra("movieid", movie?.id)
            view.context.startActivity(intent)
        }

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: ModelMovie) {
            this.movie = movie
            val imageUrl = StringBuilder()
            imageUrl.append(view.context.getString(R.string.base_path_poster)).append(movie.posterPath)
            view.mvTitle.setText(movie.originalTitle)
            Glide.with(view.context).load(imageUrl.toString()).into(view.mvPoster)
        }
    }
}