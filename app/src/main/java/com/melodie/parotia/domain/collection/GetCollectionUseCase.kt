package com.melodie.parotia.domain.collection

import com.melodie.parotia.di.IoDispatcher
import com.melodie.parotia.domain.UseCase
import com.melodie.parotia.model.Collection
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetCollectionUseCase @Inject constructor(
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, Collection>(dispatcher) {
    override suspend fun execute(parameters: String): Collection {
        TODO("Not yet implemented")
    }
}
