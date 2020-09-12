package com.melodie.parotia.model

import android.os.Parcelable
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    val id: String,
    val created_at: String,
    val updated_at: String,
    val width: Int,
    val height: Int,
    val color: String,
    val views: Long,
    val downloads: Long,
    val likes: Long,
    val liked_by_user: Boolean,
    val description: String,
    val exif: Exif,
    val location: Location,
    val tags: List<Tag>,
    val urls: Urls,
    val links: PLinks,
    val user: User
) : Parcelable {
    @IgnoredOnParcel
    val dimensions = "$width x $height"
}

@Parcelize
data class Exif(
    val make: String,
    val model: String,
    val exposure_time: String,
    val aperture: String,
    val focal_length: String,
    val iso: Int
) : Parcelable

@Parcelize
data class Location(
    val city: String,
    val country: String,
    val position: Position
) : Parcelable

@Parcelize
data class Position(
    val latitude: Double,
    val longitude: Double
) : Parcelable

@Parcelize
data class Tag(
    val title: String
) : Parcelable

@Parcelize
data class Urls(
    val raw: String,
    val full: String,
    val regular: String,
    val small: String,
    val thumb: String
) : Parcelable

@Parcelize
data class PLinks(
    val self: String,
    val html: String,
    val download: String,
    val download_location: String
) : Parcelable
