package com.bemo.withingspicture.infrastructure

import com.bemo.withingspicture.repository.Image
import com.bemo.withingspicture.repository.PictureResponse
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PicturePresenterTest {

    @InjectMocks
    private lateinit var presenter: PicturePresenter

    @Mock
    private lateinit var view: PictureView

    @Test
    fun `getPictures when presenter present at least one element then view should display urls`() =
        runBlocking {
            // When
            val response = PictureResponse(
                1,
                images = listOf(
                    Image(
                        1,
                        largeImageUrl = "url",
                        webFormatHeight = 10,
                        webFormatUrl = "url",
                        webFormatWidth = 10,
                        userImageUrl = "url"
                    )
                )
            )
            presenter.presentPicture(response)

            // Then
            then(view).should(only()).displayPicture(PictureViewModel(imagesUrl = listOf("url")))
        }

    @Test
    fun `getPictures when presenter present at zero element then view should display empty`() =
        runBlocking {
            // When
            val response = PictureResponse(
                1,
                images = emptyList()
            )
            presenter.presentPicture(response)

            // Then
            then(view).should(only()).displayEmpty()
        }
}