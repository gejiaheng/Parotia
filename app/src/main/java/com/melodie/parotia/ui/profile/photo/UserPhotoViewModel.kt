package com.melodie.parotia.ui.profile.photo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import com.melodie.parotia.data.user.UserRepository
import com.melodie.parotia.domain.user.ListUserPhotosUseCase
import com.melodie.parotia.model.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class UserPhotoViewModel(private val username: LiveData<String>) : ViewModel() {
    private val listUserPhotosUseCase = ListUserPhotosUseCase(
        UserRepository(),
        Dispatchers.IO
    )

//    lateinit var photoFlow: Flow<PagingData<Photo>>

//    init {
//        viewModelScope.launch {
//            photoFlow = listUserPhotosUseCase(username)
//        }
//    }

    fun getPhotos(): Flow<PagingData<Photo>> {
        return listUserPhotosUseCase(username.value!!)
    }
}

class UserPhotoViewModelFactory(private val username: LiveData<String>) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserPhotoViewModel::class.java)) {
            return UserPhotoViewModel(username) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
