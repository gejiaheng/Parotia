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
    val user: User,
    val blur_hash: String,
) : Parcelable {
    @IgnoredOnParcel
    val dimensions = "$width x $height"

    fun suitableUrl(width: Int): String {
        return when (width) {
            in 0..200 -> urls.thumb
            in 200..400 -> urls.small
            else -> urls.regular
        }
    }
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
    /**
     * returns a base image URL with just the photo path and the ixid parameter for your API
     * application. Use this to easily add additional image parameters to construct your own image URL.
     */
    val raw: String,
    /**
     * returns the photo in jpg format with its maximum dimensions. For performance purposes,
     * we don’t recommend using this as the photos will load slowly for your users.
     */
    val full: String,
    /**
     * returns the photo in jpg format with a width of 1080 pixels.
     */
    val regular: String,
    /**
     * returns the photo in jpg format with a width of 400 pixels.
     */
    val small: String,
    /**
     * returns the photo in jpg format with a width of 200 pixels.
     */
    val thumb: String
) : Parcelable

@Parcelize
data class PLinks(
    val self: String,
    val html: String,
    val download: String,
    val download_location: String
) : Parcelable
