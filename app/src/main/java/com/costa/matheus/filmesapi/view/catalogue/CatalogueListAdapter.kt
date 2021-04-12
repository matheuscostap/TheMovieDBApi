package com.costa.matheus.filmesapi.view.catalogue

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.model.dto.GenreModel
import com.costa.matheus.filmesapi.utils.GenreImageMapper
import kotlinx.android.synthetic.main.row_catalogue.view.*

class CatalogueListAdapter (
    private val ctx: Context,
    private val genres: List<GenreModel>,
    private val onClick: (GenreModel) -> Unit): RecyclerView.Adapter<CatalogueViewHolder>(){

    private val mapper = GenreImageMapper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogueViewHolder {
        val view = LayoutInflater.from(ctx).inflate(R.layout.row_catalogue, parent, false)
        return CatalogueViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatalogueViewHolder, position: Int) {
        val model = genres[position]
        val resId = mapper.map(model)

        resId?.let {
            //holder.iv_genre_background.setImageDrawable(ctx.getDrawable(it))
            holder.iv_genre_background.load(ctx.getDrawable(it))
        }

        holder.itemView.setOnClickListener { onClick(model) }
        holder.tv_genre_name.text = model.name
    }

    override fun getItemCount(): Int {
        return genres.size
    }

}

class CatalogueViewHolder(item: View): RecyclerView.ViewHolder(item) {
    val iv_genre_background = item.iv_genre_background
    val tv_genre_name = item.tv_genre_name
}