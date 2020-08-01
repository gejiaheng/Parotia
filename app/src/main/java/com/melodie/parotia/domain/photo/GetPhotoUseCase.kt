package com.melodie.parotia.domain.photo

import com.melodie.parotia.data.photo.PhotoRepository
import com.melodie.parotia.di.IoDispatcher
import com.melodie.parotia.domain.UseCase
import com.melodie.parotia.model.Photo
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetPhotoUseCase @Inject constructor(
    private val repository: PhotoRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, Photo>(dispatcher) {
    override suspend fun execute(parameters: String): Photo {
        return repository.getPhoto(parameters)
    }
}
