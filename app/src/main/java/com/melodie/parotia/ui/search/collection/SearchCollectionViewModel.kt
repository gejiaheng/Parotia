package com.melodie.parotia.ui.search.collection

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.melodie.parotia.domain.search.SearchCollectionUseCase
import com.melodie.parotia.model.Collection
import kotlinx.coroutines.flow.Flow

class SearchCollectionViewModel @ViewModelInject constructor(
    private val searchCollectionUseCase: SearchCollectionUseCase
) : ViewModel() {
    fun searchCollection(query: String): Flow<PagingData<Collection>> = searchCollectionUseCase(query)
}
