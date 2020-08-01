package com.melodie.parotia.ui.profile.like

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import com.melodie.parotia.data.user.UserRepository
import com.melodie.parotia.domain.user.ListUserLikedPhotosUseCase
import com.melodie.parotia.model.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class UserLikedPhotoViewModel(private val username: LiveData<String>) : ViewModel() {
    private val listUserLikedPhotosUseCase = ListUserLikedPhotosUseCase(
        UserRepository(),
        Dispatchers.IO
    )

    lateinit var photoFlow: Flow<PagingData<Photo>>

//    init {
//        viewModelScope.launch {
//            photoFlow = listUserLikedPhotosUseCase(username)
//        }
//    }
    fun getLikedPhotos(): Flow<PagingData<Photo>> {
        return listUserLikedPhotosUseCase(username.value!!)
    }
}

class UserLikedPhotoViewModelFactory(private val username: LiveData<String>) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserLikedPhotoViewModel::class.java)) {
            return UserLikedPhotoViewModel(username) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
