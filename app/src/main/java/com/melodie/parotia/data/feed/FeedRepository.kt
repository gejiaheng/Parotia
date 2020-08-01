package com.melodie.parotia.data.feed

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.melodie.parotia.api.PAGE_SIZE
import com.melodie.parotia.api.STARTING_PAGE_INDEX
import com.melodie.parotia.api.UnsplashApi
import com.melodie.parotia.api.service.PhotoService
import com.melodie.parotia.data.feed.FeedPagingSource.Companion.LATEST
import com.melodie.parotia.data.feed.FeedPagingSource.Companion.POPULAR
import com.melodie.parotia.model.Photo
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FeedRepository @Inject constructor() {
    fun getPopularPhotos(): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                FeedPagingSource(
                    POPULAR
                )
            }
        ).flow
    }

    fun getLatestPhotos(): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                FeedPagingSource(
                    LATEST
                )
            }
        ).flow
    }
}

class FeedPagingSource(
    private val type: Int
) : PagingSource<Int, Photo>() {

    companion object {
        const val POPULAR = 1
        const val LATEST = 2
    }

    val service: PhotoService = UnsplashApi.photoService

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: STARTING_PAGE_INDEX
        val orderBy = when (type) {
            POPULAR -> "popular"
            LATEST -> "latest"
            else -> "popular"
        }
        return try {
            val photos = service.listPhotos(page, params.pageSize, orderBy)
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
