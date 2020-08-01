package com.melodie.parotia.domain.search

import com.melodie.parotia.data.search.SearchHistoryRepository
import com.melodie.parotia.di.IoDispatcher
import com.melodie.parotia.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SaveSearchHistoryUseCase @Inject constructor(
    private val repository: SearchHistoryRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<List<String>, Unit>(dispatcher) {
    override suspend fun execute(parameters: List<String>) {
        repository.saveHistory(parameters)
    }
}
