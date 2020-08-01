package com.melodie.parotia.domain.search

import androidx.paging.PagingData
import com.melodie.parotia.data.search.SearchCollectionRepository
import com.melodie.parotia.di.IoDispatcher
import com.melodie.parotia.domain.PagingUseCase
import com.melodie.parotia.model.Collection
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchCollectionUseCase @Inject constructor(
    private val repository: SearchCollectionRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : PagingUseCase<String, Collection>(dispatcher) {
    override fun execute(parameters: String): Flow<PagingData<Collection>> {
        return repository.getSearchCollectionsStream(parameters)
    }
}
