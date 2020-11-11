package com.melodie.parotia.data.user

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.melodie.parotia.api.PAGE_SIZE
import com.melodie.parotia.api.STARTING_PAGE_INDEX
import com.melodie.parotia.api.service.UserService
import com.melodie.parotia.model.Collection
import com.melodie.parotia.model.Photo
import com.melodie.parotia.model.User
import com.melodie.parotia.model.UserStats
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userService: UserService
) {
    suspend fun getMe(): User {
        return userService.getMe()
    }

    fun listUserPhotos(username: String): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                UserPhotosPagingSource(
                    username,
                    userService
                )
            }
        ).flow
    }

    fun listUserLikedPhotos(username: String): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                UserLikedPhotosPagingSource(
                    username,
                    userService
                )
            }
        ).flow
    }

    fun listUserCollections(username: String): Flow<PagingData<Collection>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                UserCollectionPagingSource(
                    username,
                    userService
                )
            }
        ).flow
    }

    suspend fun getUserStatistics(username: String): UserStats {
        return userService.getUserStatistics(username)
    }
}

class UserPhotosPagingSource(
    private val username: String,
    private val service: UserService
) : PagingSource<Int, Photo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val photos = service.listUserPhotos(username, page, params.pageSize)
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

class UserLikedPhotosPagingSource(
    private val username: String,
    private val service: UserService
) : PagingSource<Int, Photo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val photos = service.listUserLikedPhotos(username, page, params.pageSize)
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

class UserCollectionPagingSource(
    private val username: String,
    private val service: UserService
) : PagingSource<Int, Collection>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Collection> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val photos = service.listUserCollections(username, page, params.pageSize)
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
