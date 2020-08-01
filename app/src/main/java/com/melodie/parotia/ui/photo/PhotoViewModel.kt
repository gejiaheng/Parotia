package com.melodie.parotia.ui.photo

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.melodie.parotia.domain.photo.GetPhotoUseCase
import com.melodie.parotia.model.Photo
import com.melodie.parotia.result.data

class PhotoViewModel @ViewModelInject constructor(
    getPhotoUseCase: GetPhotoUseCase,
    @Assisted savedStateHandle: SavedStateHandle
) : ViewModel() {
    val id: LiveData<String> = savedStateHandle.getLiveData<String>("id")
    val photo: LiveData<Photo?> = Transformations.switchMap(id) {
        liveData {
            emit(getPhotoUseCase(it).data)
        }
    }
}
