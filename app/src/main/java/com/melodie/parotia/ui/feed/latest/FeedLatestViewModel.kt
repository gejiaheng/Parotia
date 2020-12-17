package com.melodie.parotia.ui.feed.latest

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.melodie.parotia.domain.feed.GetFeedLatestUseCase
import com.melodie.parotia.model.Photo
import kotlinx.coroutines.flow.Flow

class FeedLatestViewModel @ViewModelInject constructor(
    getFeedLatestUseCase: GetFeedLatestUseCase
) : ViewModel() {
    val photos: Flow<PagingData<Photo>> by lazy {
        getFeedLatestUseCase(Unit).cachedIn(viewModelScope)
    }
}
