package com.melodie.parotia.api.service

import com.melodie.parotia.model.Collection
import com.melodie.parotia.model.Photo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CollectionService {
    @GET("/collections/featured")
    suspend fun listFeaturedCollections(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<Collection>

    @GET("/collections/{id}/photos")
    suspend fun getCollectionPhotos(
        @Path("id") id: Long,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<Photo>

    // This API is not paginated
    @GET("/collections/{id}/related")
    suspend fun listRelatedCollections(
        @Path("id") id: Long
    ): List<Collection>
}
