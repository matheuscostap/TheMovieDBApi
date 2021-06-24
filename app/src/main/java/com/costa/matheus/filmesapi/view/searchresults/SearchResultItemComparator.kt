package com.costa.matheus.filmesapi.view.searchresults

import androidx.recyclerview.widget.DiffUtil
import com.costa.matheus.filmesapi.model.dto.MovieModel

object SearchResultItemComparator : DiffUtil.ItemCallback<MovieModel>() {
    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem == newItem
    }
}
