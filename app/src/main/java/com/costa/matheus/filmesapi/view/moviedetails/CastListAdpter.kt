package com.costa.matheus.filmesapi.view.moviedetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.model.dto.CastModel
import com.costa.matheus.filmesapi.utils.Constants
import kotlinx.android.synthetic.main.row_cast.view.*

class CastListAdpter (
    private val ctx: Context,
    private val castList: List<CastModel>): RecyclerView.Adapter<CastListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastListViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.row_cast, parent, false)
        return CastListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CastListViewHolder, position: Int) {
        val model = castList[position]

        holder.tv_cast_name.text = model.name
        holder.iv_cast_photo.load("${Constants.imagePath1280}${model.profile_path}"){
            crossfade(true)
            transformations(CircleCropTransformation())
        }
    }

    override fun getItemCount(): Int {
        return castList.size
    }

}

class CastListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val iv_cast_photo = itemView.iv_cast_photo
    val tv_cast_name = itemView.tv_cast_name
}