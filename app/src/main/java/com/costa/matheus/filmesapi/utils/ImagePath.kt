package com.costa.matheus.filmesapi.utils

import com.costa.matheus.filmesapi.repository.settings.LocalDataRepository

class ImagePath (private val localDataRepository: LocalDataRepository) {

    fun getFinalPath(imageName: String?): String{
        return "${getPathByImageSetting()}$imageName"
    }

    private fun getPathByImageSetting(): String {
        if (localDataRepository.isHqImageEnabled()) {
            return Constants.imagePath1280
        }else{
            return Constants.imagePath500
        }
    }

}