package com.melodie.parotia.api.service

import com.melodie.parotia.model.Collection
import com.melodie.parotia.model.Photo
import com.melodie.parotia.model.SearchResult
import com.melodie.parotia.model.User
import retrofit2.http.GET
import retrofit2.http.Query

const val ORDER_BY_RELEVANT = "relevant"
const val ORDER_BY_LATEST = "latest"

const val CONTENT_FILTER_LOW = "low"
const val CONTENT_FILTER_HIGH = "high"

interface SearchService {

    @GET("/search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String = ORDER_BY_RELEVANT,
        @Query("content_filter") contentFilter: String = CONTENT_FILTER_LOW
//        @Query("color") color: String = "",
//        @Query("orientation") orientation: String = ""
    ): SearchResult<Photo>

    @GET("/search/collections")
    suspend fun searchCollections(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): SearchResult<Collection>

    @GET("/search/users")
    suspend fun searchUsers(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): SearchResult<User>
}
