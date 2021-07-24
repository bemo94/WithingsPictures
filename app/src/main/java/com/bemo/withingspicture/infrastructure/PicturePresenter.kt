package com.bemo.withingspicture.infrastructure

import com.bemo.withingspicture.repository.PictureResponse

class PicturePresenter(private val view: PictureView) {

    fun presentPicture(pictures: PictureResponse) {
        view.displayPicture(
            PictureViewModel(imagesUrl = pictures.images.map { it.userImageUrl })
        )
    }
}

data class PictureViewModel(
    val imagesUrl: List<String>
)
