package com.bemo.withingspicture.infrastructure

import com.bemo.withingspicture.interactor.PictureInteractor

class PictureController (private val interactor: PictureInteractor){

    suspend fun getPictures(query: String) {
        interactor.getPictures(query)
    }

}