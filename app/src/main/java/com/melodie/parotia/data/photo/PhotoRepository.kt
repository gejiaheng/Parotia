package com.melodie.parotia.data.photo

import com.melodie.parotia.api.service.PhotoService
import com.melodie.parotia.model.Photo
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val photoService: PhotoService
) {
    suspend fun getPhoto(id: String): Photo {
        return photoService.getPhoto(id)
    }
}
