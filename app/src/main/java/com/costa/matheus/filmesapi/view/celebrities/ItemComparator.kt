package com.costa.matheus.filmesapi.view.celebrities

import androidx.recyclerview.widget.DiffUtil
import com.costa.matheus.filmesapi.model.dto.CelebrityModel

object ItemComparator : DiffUtil.ItemCallback<CelebrityModel>() {
    override fun areItemsTheSame(oldItem: CelebrityModel, newItem: CelebrityModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CelebrityModel, newItem: CelebrityModel): Boolean {
        return oldItem == newItem
    }
}
