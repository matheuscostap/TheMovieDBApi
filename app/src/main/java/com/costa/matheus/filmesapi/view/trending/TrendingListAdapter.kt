package com.costa.matheus.filmesapi.view.trending

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.model.dto.MovieModel
import com.costa.matheus.filmesapi.utils.Constants
import kotlinx.android.synthetic.main.trending_movie_row.view.*

class TrendingListAdapter (
    val ctx: Context,
    private val items: List<MovieModel>): RecyclerView.Adapter<TrendingListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingListViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.trending_movie_row, parent, false)
        return TrendingListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrendingListViewHolder, position: Int) {
        val movie = items[position]

        holder.tv_movie_name.text = movie.title
        holder.iv_movie_poster.load("${Constants.imagePath1280}${movie.backdrop_path}")
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class TrendingListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val tv_movie_name = itemView.tv_movie_name
    val iv_movie_poster = itemView.iv_movie_poster
}