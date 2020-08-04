package com.melodie.parotia.model

data class Photo(
    val id: String,
    val created_at: String,
    val updated_at: String,
    val width: Int,
    val height: Int,
    val color: String,
    val downloads: Int,
    val likes: Int,
    val liked_by_user: Boolean,
    val description: String,
    val exif: Exif,
    val location: Location,
    val tags: List<Tag>,
    val urls: Urls,
    val links: PLinks,
    val user: User
)

data class Exif(
    val make: String,
    val model: String,
    val exposure_time: String,
    val aperture: String,
    val focal_length: String,
    val iso: Int
)

data class Location(
    val city: String,
    val country: String,
    val position: Position
)

data class Position(
    val latitude: Double,
    val longitude: Double
)

data class Tag(
    val title: String
)

data class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
)

data class PLinks(
    val self: String,
    val html: String,
    val download: String,
    val download_location: String
)
