package com.bemo.withingspicture.interactor

import com.bemo.withingspicture.infrastructure.PicturePresenter
import com.bemo.withingspicture.repository.PictureRepository
import com.bemo.withingspicture.repository.PictureResponse
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PictureInteractorTest {

    @Mock
    private lateinit var presenter: PicturePresenter

    @Mock
    private lateinit var repository: PictureRepository

    @InjectMocks
    private lateinit var interactor: PictureInteractor

    @Test
    fun getPictures() = runBlocking {
        // Given
        val repoResponse = mock(PictureResponse::class.java)
        given(repository.fetchPictures("query")).willReturn(repoResponse)

        // When
        interactor.getPictures("query")

        // Then
        then(presenter).should(only()).presentPicture(repoResponse)
    }
}