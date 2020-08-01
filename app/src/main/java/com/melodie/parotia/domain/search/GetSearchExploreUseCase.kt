package com.melodie.parotia.domain.search

import com.melodie.parotia.data.search.SearchExploreRepository
import com.melodie.parotia.di.MainDispatcher
import com.melodie.parotia.domain.UseCase
import com.melodie.parotia.model.SearchExplore
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetSearchExploreUseCase @Inject constructor(
    private val repository: SearchExploreRepository,
    @MainDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, List<SearchExplore>>(dispatcher) {
    override suspend fun execute(parameters: Unit): List<SearchExplore> {
        return repository.getSearchExplores()
    }
}
