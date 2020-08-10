package com.melodie.parotia.ui.profile.photo

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.melodie.parotia.domain.user.ListUserPhotosUseCase
import com.melodie.parotia.model.Photo
import kotlinx.coroutines.flow.Flow

class UserPhotoViewModel @ViewModelInject constructor(
    private val listUserPhotosUseCase: ListUserPhotosUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun getPhotos(): Flow<PagingData<Photo>> {
        return listUserPhotosUseCase(savedStateHandle.get("username")!!)
    }
}
