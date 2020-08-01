package com.melodie.parotia.ui.collection.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.melodie.parotia.domain.collection.GetFeaturedCollectionUseCase
import com.melodie.parotia.model.Collection
import kotlinx.coroutines.flow.Flow

class CollectionListViewModel @ViewModelInject constructor(
    getFeaturedCollectionUseCase: GetFeaturedCollectionUseCase
) : ViewModel() {
    val collections: Flow<PagingData<Collection>> = getFeaturedCollectionUseCase(Unit)
}
