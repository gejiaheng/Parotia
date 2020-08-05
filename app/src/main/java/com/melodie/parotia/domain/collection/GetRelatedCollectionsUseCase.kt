package com.melodie.parotia.domain.collection

import com.melodie.parotia.data.collection.CollectionRepository
import com.melodie.parotia.di.IoDispatcher
import com.melodie.parotia.domain.UseCase
import com.melodie.parotia.model.Collection
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetRelatedCollectionsUseCase @Inject constructor(
    private val repository: CollectionRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Long, List<Collection>>(dispatcher) {
    override suspend fun execute(parameters: Long): List<Collection> {
        return repository.getRelatedCollections(parameters)
    }
}
