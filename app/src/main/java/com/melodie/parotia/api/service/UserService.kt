package com.melodie.parotia.api.service

import com.melodie.parotia.model.Collection
import com.melodie.parotia.model.Photo
import com.melodie.parotia.model.User
import com.melodie.parotia.model.UserStats
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("/me")
    suspend fun getMe(): User

    @GET("/users/{username}/photos")
    suspend fun listUserPhotos(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<Photo>

    @GET("/users/{username}/likes")
    suspend fun listUserLikedPhotos(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<Photo>

    @GET("/users/{username}/collections")
    suspend fun listUserCollections(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<Collection>

    @GET("/users/{username}/statistics")
    suspend fun getUserStatistics(
        @Path("username") username: String,
        @Query("resolution") resolution: String = "days",
        @Query("quantity") quantity: Int = 30
    ): UserStats
}
