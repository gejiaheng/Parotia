package com.melodie.parotia.domain.search

import com.melodie.parotia.data.photo.PhotoRepository
import com.melodie.parotia.di.IoDispatcher
import com.melodie.parotia.domain.UseCase
import com.melodie.parotia.model.SearchBanner
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetSearchBannersUseCase @Inject constructor(
    private val repository: PhotoRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Any, List<SearchBanner>>(dispatcher) {
    override suspend fun execute(parameters: Any): List<SearchBanner> {
        return repository.getSearchBanners()
    }
}
