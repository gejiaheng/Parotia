package com.melodie.parotia.ui.collection.detail

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.melodie.parotia.domain.collection.GetCollectionPhotosUseCase
import com.melodie.parotia.domain.collection.GetCollectionUseCase
import com.melodie.parotia.model.args.ArgCollection

// https://medium.com/mobile-app-development-publication/passing-intent-data-to-viewmodel-711d72db20ad
class CollectionViewModel @ViewModelInject constructor(
    getCollectionUseCase: GetCollectionUseCase,
    getCollectionPhotosUseCase: GetCollectionPhotosUseCase,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {
    val collection = savedStateHandle.get<ArgCollection>("model")
    val photos = collection?.id?.let { getCollectionPhotosUseCase(it) }
}
