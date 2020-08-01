package com.melodie.parotia.ui.search.photo

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.melodie.parotia.domain.search.SearchPhotoParams
import com.melodie.parotia.domain.search.SearchPhotoUseCase
import com.melodie.parotia.model.Photo
import kotlinx.coroutines.flow.Flow

class SearchPhotoViewModel @ViewModelInject constructor(
    private val searchPhotoUseCase: SearchPhotoUseCase
) : ViewModel() {
    fun searchPhoto(query: String): Flow<PagingData<Photo>> {
        val params = SearchPhotoParams(query, null, null, null)
        return searchPhotoUseCase(params)
    }
}
