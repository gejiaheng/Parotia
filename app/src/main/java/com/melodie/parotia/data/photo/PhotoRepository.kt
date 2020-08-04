package com.melodie.parotia.data.photo

import com.google.gson.Gson
import com.melodie.parotia.api.service.PhotoService
import com.melodie.parotia.data.prefs.SharedPreferenceStorage
import com.melodie.parotia.model.Photo
import com.melodie.parotia.model.SearchBanner
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val photoService: PhotoService,
    private val preferences: SharedPreferenceStorage,
    private val gson: Gson
) {
    suspend fun getPhoto(id: String): Photo {
        return photoService.getPhoto(id)
    }

    private suspend fun getRandomPhotos(count: Int): List<Photo> {
        return photoService.getRandomPhotos(count)
    }

    suspend fun getSearchBanners(): List<SearchBanner> {
        val saved =
            gson.fromJson(preferences.searchBanners, Array<SearchBanner>::class.java)?.toList()
                ?: emptyList()
        val fresh = mutableListOf<SearchBanner>()
        if (saved == null || saved.size < BANNERS_THRESHOLD_COUNT) {
            val photos = getRandomPhotos(BANNERS_REQUEST_COUNT)
            fresh.addAll(photos.map { SearchBanner.fromPhoto(it) })
        }
        return listOf(saved, fresh).flatten()
    }

    fun saveSearchBanners(banners: List<SearchBanner>) {
        preferences.searchBanners = gson.toJson(banners)
    }

    companion object {
        const val BANNERS_REQUEST_COUNT = 30
        const val BANNERS_THRESHOLD_COUNT = 10
    }
}
