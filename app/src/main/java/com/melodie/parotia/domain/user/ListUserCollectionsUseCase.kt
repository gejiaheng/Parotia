package com.melodie.parotia.domain.user

import androidx.paging.PagingData
import com.melodie.parotia.data.user.UserRepository
import com.melodie.parotia.domain.PagingUseCase
import com.melodie.parotia.model.Collection
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class ListUserCollectionsUseCase(
    private val repository: UserRepository,
    dispatcher: CoroutineDispatcher
) : PagingUseCase<String, Collection>(dispatcher) {
    override fun execute(parameters: String): Flow<PagingData<Collection>> {
        return repository.listUserCollections(parameters)
    }
}
