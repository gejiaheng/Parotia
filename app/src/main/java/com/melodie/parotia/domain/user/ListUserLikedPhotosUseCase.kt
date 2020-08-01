package com.melodie.parotia.domain.user

import androidx.paging.PagingData
import com.melodie.parotia.data.user.UserRepository
import com.melodie.parotia.domain.PagingUseCase
import com.melodie.parotia.model.Photo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class ListUserLikedPhotosUseCase(
    private val repository: UserRepository,
    dispatcher: CoroutineDispatcher
) : PagingUseCase<String, Photo>(dispatcher) {
    override fun execute(parameters: String): Flow<PagingData<Photo>> {
        return repository.listUserLikedPhotos(parameters)
    }
}
