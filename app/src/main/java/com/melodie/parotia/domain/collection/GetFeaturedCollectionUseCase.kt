package com.melodie.parotia.domain.collection

import androidx.paging.PagingData
import com.melodie.parotia.data.collection.CollectionRepository
import com.melodie.parotia.di.IoDispatcher
import com.melodie.parotia.domain.PagingUseCase
import com.melodie.parotia.model.Collection
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFeaturedCollectionUseCase @Inject constructor(
    private val repository: CollectionRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : PagingUseCase<Any, Collection>(dispatcher) {
    override fun execute(parameters: Any): Flow<PagingData<Collection>> {
        return repository.getFeaturedCollections()
    }
}
