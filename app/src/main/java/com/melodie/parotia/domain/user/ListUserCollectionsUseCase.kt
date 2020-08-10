package com.melodie.parotia.domain.user

import androidx.paging.PagingData
import com.melodie.parotia.data.user.UserRepository
import com.melodie.parotia.di.IoDispatcher
import com.melodie.parotia.domain.PagingUseCase
import com.melodie.parotia.model.Collection
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListUserCollectionsUseCase @Inject constructor(
    private val repository: UserRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : PagingUseCase<String, Collection>(dispatcher) {
    override fun execute(parameters: String): Flow<PagingData<Collection>> {
        return repository.listUserCollections(parameters)
    }
}
