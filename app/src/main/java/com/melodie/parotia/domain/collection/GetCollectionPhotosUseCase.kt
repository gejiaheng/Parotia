package com.melodie.parotia.domain.collection

import androidx.paging.PagingData
import com.melodie.parotia.data.collection.CollectionRepository
import com.melodie.parotia.di.IoDispatcher
import com.melodie.parotia.domain.PagingUseCase
import com.melodie.parotia.model.Photo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollectionPhotosUseCase @Inject constructor(
    private val repository: CollectionRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : PagingUseCase<Long, Photo>(dispatcher) {
    override fun execute(parameters: Long): Flow<PagingData<Photo>> {
        return repository.getCollectionPhotos(parameters)
    }
}
