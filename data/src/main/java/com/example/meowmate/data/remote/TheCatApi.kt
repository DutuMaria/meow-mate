package com.example.meowmate.data.remote


import com.example.meowmate.data.remote.dto.CatDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface TheCatApi {
    @GET("v1/images/search")
    suspend fun getImages(
        @Header("x-api-key") apiKey: String? = null,
        @Query("limit") limit: Int = 20,
        @Query("has_breeds") hasBreeds: Int = 1,
        @Query("page") page: Int? = null,
        @Query("order") order: String? = null,
    ): List<CatDto>

    @GET("v1/images/{id}")
    suspend fun getImageById(
        @Header("x-api-key") apiKey: String? = null,
        @Path("id") id: String
    ): CatDto
}
