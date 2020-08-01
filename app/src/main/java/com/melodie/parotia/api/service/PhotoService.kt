package com.melodie.parotia.api.service

import com.melodie.parotia.model.Photo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoService {
    @GET("/photos")
    suspend fun listPhotos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String
    ): List<Photo>

    @GET("/photos/{id}")
    suspend fun getPhoto(
        @Path("id") id: String
    ): Photo
}
