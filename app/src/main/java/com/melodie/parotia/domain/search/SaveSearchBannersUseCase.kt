package com.melodie.parotia.domain.search

import com.melodie.parotia.data.photo.PhotoRepository
import com.melodie.parotia.di.IoDispatcher
import com.melodie.parotia.domain.UseCase
import com.melodie.parotia.model.SearchBanner
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SaveSearchBannersUseCase @Inject constructor(
    private val repository: PhotoRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<List<SearchBanner>, Unit>(dispatcher) {
    override suspend fun execute(parameters: List<SearchBanner>) {
        repository.saveSearchBanners(parameters.filter { !it.shown })
    }
}
