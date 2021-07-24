package com.bemo.withingspicture.infrastructure

import com.bemo.withingspicture.interactor.PictureInteractor
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.only
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PictureControllerTest {

    @Mock
    private lateinit var interactor: PictureInteractor

    @InjectMocks
    private lateinit var controller: PictureController

    @Test
    fun getPictures() = runBlocking {
        // When
        controller.getPictures("query")

        // Then
        then(interactor).should(only()).getPictures("query")
    }

}