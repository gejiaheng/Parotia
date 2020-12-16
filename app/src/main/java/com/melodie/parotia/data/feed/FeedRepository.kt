package com.melodie.parotia.data.feed

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.melodie.parotia.api.INITIAL_LOAD_SIZE
import com.melodie.parotia.api.PAGE_SIZE
import com.melodie.parotia.api.STARTING_PAGE_INDEX
import com.melodie.parotia.api.service.PhotoService
import com.melodie.parotia.model.Photo
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FeedRepository @Inject constructor(
    val service: PhotoService
) {
    fun getPopularPhotos(): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = INITIAL_LOAD_SIZE
            ),
            pagingSourceFactory = { PopularPagingSource(service) }
        ).flow
    }

    fun getLatestPhotos(): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = INITIAL_LOAD_SIZE
            ),
            pagingSourceFactory = { LatestPagingSource(service) }
        ).flow
    }
}

abstract class FeedPagingSource(val service: PhotoService) : PagingSource<Int, Photo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val photos = service.listPhotos(page, params.loadSize, orderBy())
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

    abstract fun orderBy(): String
}

class PopularPagingSource(
    service: PhotoService
) : FeedPagingSource(service) {
    override fun orderBy() = "popular"
}

class LatestPagingSource(
    service: PhotoService
) : FeedPagingSource(service) {
    override fun orderBy() = "latest"
}
