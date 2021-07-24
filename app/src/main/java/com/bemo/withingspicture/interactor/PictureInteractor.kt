package com.bemo.withingspicture.interactor

import com.bemo.withingspicture.infrastructure.PicturePresenter
import com.bemo.withingspicture.repository.PictureRepository

class PictureInteractor(
    private val repository: PictureRepository,
    private val presenter: PicturePresenter
) {
    suspend fun getPictures(query: String) {
        val pictures = repository.fetchPictures(query)
        presenter.presentPicture(pictures)
    }
}