package com.melodie.parotia.ui.profile.like

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.melodie.parotia.domain.user.ListUserLikedPhotosUseCase
import com.melodie.parotia.model.Photo
import kotlinx.coroutines.flow.Flow

class UserLikedPhotoViewModel @ViewModelInject constructor(
    private val listUserLikedPhotosUseCase: ListUserLikedPhotosUseCase,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    fun getLikedPhotos(): Flow<PagingData<Photo>> {
        return listUserLikedPhotosUseCase(savedStateHandle.get("username")!!)
    }
}
