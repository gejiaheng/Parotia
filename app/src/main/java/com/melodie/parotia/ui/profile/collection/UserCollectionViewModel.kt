package com.melodie.parotia.ui.profile.collection

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.melodie.parotia.domain.user.ListUserCollectionsUseCase
import com.melodie.parotia.model.Collection
import kotlinx.coroutines.flow.Flow

class UserCollectionViewModel @ViewModelInject constructor(
    private val listUserCollectionsUseCase: ListUserCollectionsUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    fun getCollections(): Flow<PagingData<Collection>> {
        return listUserCollectionsUseCase(savedStateHandle.get("username")!!)
    }
}
