package com.melodie.parotia.data.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.melodie.parotia.api.PAGE_SIZE
import com.melodie.parotia.api.STARTING_PAGE_INDEX
import com.melodie.parotia.api.service.SearchService
import com.melodie.parotia.model.Collection
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchCollectionRepository @Inject constructor(
    private val service: SearchService
) {
    fun getSearchCollectionsStream(query: String): Flow<PagingData<Collection>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                SearchCollectionPagingSource(
                    service,
                    query
                )
            }
        ).flow
    }
}

class SearchCollectionPagingSource(
    private val service: SearchService,
    private val query: String
) : PagingSource<Int, Collection>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Collection> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val collections = service.searchCollections(query, page, params.loadSize)
            LoadResult.Page(
                data = collections.results,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (collections.results.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
