package com.melodie.parotia.ui.collection.detail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.melodie.parotia.domain.collection.GetCollectionPhotosUseCase
import com.melodie.parotia.domain.collection.GetCollectionUseCase
import com.melodie.parotia.domain.collection.GetRelatedCollectionsUseCase
import com.melodie.parotia.model.Collection
import com.melodie.parotia.model.args.ArgCollection
import com.melodie.parotia.result.data

// https://medium.com/mobile-app-development-publication/passing-intent-data-to-viewmodel-711d72db20ad
class CollectionViewModel @ViewModelInject constructor(
    getCollectionUseCase: GetCollectionUseCase,
    getCollectionPhotosUseCase: GetCollectionPhotosUseCase,
    getRelatedCollectionsUseCase: GetRelatedCollectionsUseCase,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {
    val collection = savedStateHandle.get<ArgCollection>("model")
    val photos = collection?.id?.let { getCollectionPhotosUseCase(it) }
    private val _relatedCollections: MutableLiveData<List<Collection>> = MutableLiveData()
    val relatedCollections = collection?.id?.let {
        liveData {
            emit(getRelatedCollectionsUseCase(it).data)
        }
    }
}
