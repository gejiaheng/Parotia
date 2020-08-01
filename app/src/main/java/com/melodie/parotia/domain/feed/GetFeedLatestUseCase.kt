package com.melodie.parotia.domain.feed

import androidx.paging.PagingData
import com.melodie.parotia.data.feed.FeedRepository
import com.melodie.parotia.di.IoDispatcher
import com.melodie.parotia.domain.PagingUseCase
import com.melodie.parotia.model.Photo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFeedLatestUseCase @Inject constructor(
    private val repository: FeedRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : PagingUseCase<Any, Photo>(dispatcher) {
    override fun execute(parameters: Any): Flow<PagingData<Photo>> {
        return repository.getLatestPhotos()
    }
}
