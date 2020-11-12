package com.melodie.parotia.data.collection

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.melodie.parotia.api.PAGE_SIZE
import com.melodie.parotia.api.STARTING_PAGE_INDEX
import com.melodie.parotia.api.service.CollectionService
import com.melodie.parotia.model.Collection
import com.melodie.parotia.model.Photo
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CollectionRepository @Inject constructor(
    private val service: CollectionService
) {
    fun getFeaturedCollections(): Flow<PagingData<Collection>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                CollectionListPagingSource(
                    service
                )
            }
        ).flow
    }

    fun getCollectionPhotos(id: Long): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                CollectionPhotosPagingSource(id, service)
            }
        ).flow
    }

    suspend fun getRelatedCollections(id: Long): List<Collection> {
        return service.listRelatedCollections(id)
    }
}

class CollectionListPagingSource(
    private val service: CollectionService
) : PagingSource<Int, Collection>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Collection> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val collections = service.listFeaturedCollections(page, params.loadSize)
            LoadResult.Page(
                data = collections,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (collections.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}

class CollectionPhotosPagingSource(
    private val id: Long,
    private val service: CollectionService
) : PagingSource<Int, Photo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val photos = service.getCollectionPhotos(id, page, PAGE_SIZE)
            LoadResult.Page(
                data = photos,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (photos.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
