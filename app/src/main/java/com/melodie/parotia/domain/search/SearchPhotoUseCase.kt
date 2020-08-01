package com.melodie.parotia.domain.search

import androidx.paging.PagingData
import com.melodie.parotia.data.search.SearchPhotoRepository
import com.melodie.parotia.di.IoDispatcher
import com.melodie.parotia.domain.PagingUseCase
import com.melodie.parotia.model.Photo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class SearchPhotoParams(
    val query: String,
    val orderBy: String?,
    val color: String?,
    val orientation: String?
)

class SearchPhotoUseCase @Inject constructor(
    private val repository: SearchPhotoRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : PagingUseCase<SearchPhotoParams, Photo>(dispatcher) {
    override fun execute(parameters: SearchPhotoParams): Flow<PagingData<Photo>> {
        return repository.getSearchPhotosStream(parameters)
    }
}
