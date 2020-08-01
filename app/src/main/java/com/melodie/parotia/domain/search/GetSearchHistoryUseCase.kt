package com.melodie.parotia.domain.search

import com.melodie.parotia.data.search.SearchHistoryRepository
import com.melodie.parotia.di.IoDispatcher
import com.melodie.parotia.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetSearchHistoryUseCase @Inject constructor(
    private val repository: SearchHistoryRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Any, List<String>>(dispatcher) {
    override suspend fun execute(parameters: Any): List<String> {
        return repository.getHistory()
    }
}
