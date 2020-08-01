package com.melodie.parotia.data.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.melodie.parotia.api.PAGE_SIZE
import com.melodie.parotia.api.STARTING_PAGE_INDEX
import com.melodie.parotia.api.service.SearchService
import com.melodie.parotia.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchUserRepository @Inject constructor(
    private val service: SearchService
) {
    fun getSearchUsersStream(query: String): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                SearchUserPagingSource(
                    service,
                    query
                )
            }
        ).flow
    }
}

class SearchUserPagingSource(
    private val service: SearchService,
    private val query: String
) : PagingSource<Int, User>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val users = service.searchUsers(query, page, params.pageSize)
            LoadResult.Page(
                data = users.results,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (users.results.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}
