package com.melodie.parotia.ui.collection.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.melodie.parotia.domain.collection.GetFeaturedCollectionUseCase
import com.melodie.parotia.model.Collection
import kotlinx.coroutines.flow.Flow

class CollectionListViewModel @ViewModelInject constructor(
    getFeaturedCollectionUseCase: GetFeaturedCollectionUseCase
) : ViewModel() {
    val collections: Flow<PagingData<Collection>> by lazy {
        getFeaturedCollectionUseCase(Unit).cachedIn(viewModelScope)
    }
}
