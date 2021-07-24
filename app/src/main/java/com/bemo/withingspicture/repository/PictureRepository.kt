package com.bemo.withingspicture.repository

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


class PictureRepository {
    suspend fun fetchPictures(query: String): PictureResponse {
        val builder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://pixabay.com/").build()
        val api = builder.create(PixabayApi::class.java)
        return api.getImageResults(page = 1, perPage = 40, query = query)
    }

}

interface PixabayApi {
    @GET("/api/")
    suspend fun getImageResults(
        @Query("key") key: String? = "5511001-7691b591d9508e60ec89b63c4",
        @Query("q") query: String?,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): PictureResponse
}

data class PictureResponse(
    val total: Int,

    @SerializedName("hits")
    val images: List<Image>
)

data class Image(
    val id: Int,

    @SerializedName("largeImageURL")
    val largeImageUrl: String,

    @SerializedName("webformatURL")
    val webFormatUrl: String,

    @SerializedName("webformatWidth")
    val webFormatWidth: Int,

    @SerializedName("webformatHeight")
    val webFormatHeight: Int,

    @SerializedName("userImageURL")
    val userImageUrl: String,
)