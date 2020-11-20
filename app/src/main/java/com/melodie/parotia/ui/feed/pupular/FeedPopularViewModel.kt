package com.melodie.parotia.ui.feed.pupular

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.melodie.parotia.domain.feed.GetFeedPopularUseCase
import com.melodie.parotia.model.Photo
import kotlinx.coroutines.flow.Flow

class FeedPopularViewModel @ViewModelInject constructor(
    getFeedPopularUseCase: GetFeedPopularUseCase
) : ViewModel() {
    val photos: Flow<PagingData<Photo>> = getFeedPopularUseCase(Unit)

    init {
        refresh()
    }

    fun refresh() {

    }
}
