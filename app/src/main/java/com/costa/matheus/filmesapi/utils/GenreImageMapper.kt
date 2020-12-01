package com.costa.matheus.filmesapi.utils

import android.graphics.drawable.Drawable
import com.costa.matheus.filmesapi.R
import com.costa.matheus.filmesapi.model.dto.GenreModel

class GenreImageMapper {

    fun map(genre: GenreModel): Int? {
        return when (genre.id) {
            28L -> R.drawable.acao
            12L -> R.drawable.aventura
            16L -> R.drawable.animacao
            35L -> R.drawable.comedia
            80L -> R.drawable.crime
            99L -> R.drawable.documentario
            18L -> R.drawable.drama
            10751L -> R.drawable.familia
            14L -> R.drawable.fantasia
            36L -> R.drawable.historia
            27L -> R.drawable.terror
            10402L -> R.drawable.musica
            9648L -> R.drawable.misterio
            10749L -> R.drawable.romance
            878L -> R.drawable.ficcao
            10770L -> R.drawable.tvcinema
            53L -> R.drawable.thriller
            10752L -> R.drawable.guerra
            37L -> R.drawable.faroeste
            else -> null
        }
    }

}