package com.costa.matheus.filmesapi.view.movielist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.model.dto.MovieModel
import com.costa.matheus.filmesapi.utils.convertDate
import kotlinx.android.synthetic.main.row_movie.view.*

class MovieListAdapter(val ctx: Context, val onClick: (movie: MovieModel, imageView: ImageView) -> Unit): PagedListAdapter<MovieModel, MovieViewHolder>(itemComparator){

    companion object {
        val itemComparator = object : DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem.id === newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.row_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)

        movie?.let {
            holder.tvName.text = movie.name
            holder.tvVotes.text = movie.vote_average.toString()
            holder.tvDate.text = movie.first_air_date.convertDate()
            val path = "https://image.tmdb.org/t/p/w200"
            holder.ivPoster.load("$path${movie.poster_path}")
            holder.ivBack.load("$path${movie.backdrop_path}")

            ViewCompat.setTransitionName(holder.ivPoster, it.id.toString())

            holder.itemView.setOnClickListener {
                onClick(movie, holder.ivPoster)
            }
        }
    }
}



class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val tvName = itemView.tvName
    val tvVotes = itemView.tvVotes
    val tvDate = itemView.tvDate
    val ivPoster = itemView.ivPoster
    val ivBack  = itemView.ivBack
}