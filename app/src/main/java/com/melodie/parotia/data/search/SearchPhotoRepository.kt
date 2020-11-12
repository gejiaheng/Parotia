package com.melodie.parotia.data.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.melodie.parotia.api.PAGE_SIZE
import com.melodie.parotia.api.STARTING_PAGE_INDEX
import com.melodie.parotia.api.service.SearchService
import com.melodie.parotia.domain.search.SearchPhotoParams
import com.melodie.parotia.model.Photo
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchPhotoRepository @Inject constructor(
    private val service: SearchService
) {
    fun getSearchPhotosStream(params: SearchPhotoParams): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                SearchPhotoPagingSource(
                    service,
                    params
                )
            }
        ).flow
    }
}

class SearchPhotoPagingSource(
    private val service: SearchService,
    private val searchPhotoParams: SearchPhotoParams
) : PagingSource<Int, Photo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val (query, orderBy, color, orientation) = searchPhotoParams
            val photos = service.searchPhotos(
                query,
                page,
                params.loadSize
//                orderBy ?: "",
//                color = color ?: "",
//                orientation = orientation ?: ""
            )
            LoadResult.Page(
                data = photos.results,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (photos.results.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
