package com.costa.matheus.filmesapi.view.moviedetails

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.model.MovieModel
import kotlinx.android.synthetic.main.row_similar.view.*

class SimilarListAdapter (
    val context: Context,
    val movies: List<MovieModel>): RecyclerView.Adapter<SimilarViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_similar, parent, false)
        return SimilarViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
        val movie = movies[position]
        val path = "https://image.tmdb.org/t/p/w200${movie.poster_path}"
        holder.ivPoster.load(path)
        Log.i("SimilarAdapter", "onBindVh() -> $movie")
    }

}

class SimilarViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val ivPoster = itemView.ivPoster
}