package com.costa.matheus.filmesapi.view.searchresults

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.RoundedCornersTransformation
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.model.dto.MovieModel
import com.costa.matheus.filmesapi.utils.Constants
import kotlinx.android.synthetic.main.row_search_result.view.*

class SearchResultsListAdapter (
    private val ctx: Context,
    diffCallback: DiffUtil.ItemCallback<MovieModel>
) : PagingDataAdapter<MovieModel, SearchResultsViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
        val model = getItem(position)

        model?.let {
            holder.iv_movie_poster_search.load("${Constants.imagePath1280}${it.poster_path}") {
                crossfade(true)
                transformations(
                    RoundedCornersTransformation(
                        topLeft = 15f,
                        topRight = 15f,
                        bottomLeft = 15f,
                        bottomRight = 15f
                    )
                )
            }

            holder.tv_movie_name_search.text = it.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.row_search_result, parent, false)
        return SearchResultsViewHolder(view)
    }

}

class SearchResultsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val iv_movie_poster_search = itemView.iv_movie_poster_search
    val tv_movie_name_search = itemView.tv_movie_name_search
}