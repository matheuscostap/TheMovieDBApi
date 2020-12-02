package com.costa.matheus.filmesapi.view.celebrities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.model.dto.CelebrityModel
import com.costa.matheus.filmesapi.utils.Constants
import kotlinx.android.synthetic.main.celebrities_row.view.*

class CelebritiesListAdapter (
    private val ctx: Context,
    diffCallback: DiffUtil.ItemCallback<CelebrityModel>
): PagingDataAdapter<CelebrityModel, CelebritiesViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: CelebritiesViewHolder, position: Int) {
        val model = getItem(position)

        model?.let {
            holder.tv_celebrity_name.text = it.name
            holder.iv_celebrity_photo.load("${Constants.imagePath1280}${it.profile_path}"){
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CelebritiesViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.celebrities_row, parent, false)
        return CelebritiesViewHolder(view)
    }

}

class CelebritiesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val iv_celebrity_photo = itemView.iv_celebrity_photo
    val tv_celebrity_name = itemView.tv_celebrity_name
}


