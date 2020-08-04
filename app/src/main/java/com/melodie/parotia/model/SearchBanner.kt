package com.melodie.parotia.model

data class SearchBanner(
    val url: String,
    var shown: Boolean
) {
    companion object {
        fun fromPhoto(photo: Photo) = SearchBanner(
            photo.urls.regular,
            false
        )
    }
}
