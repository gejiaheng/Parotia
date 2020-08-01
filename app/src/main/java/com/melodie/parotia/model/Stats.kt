package com.melodie.parotia.model

import com.melodie.parotia.util.CompactFormat.format
import java.io.Serializable

data class Stats(
    val photos: Long,
    val downloads: Long,
    val views: Long,
    val likes: Long,
    val photographers: Long
) : Serializable {
//    val photosCompact: String
//        get() = format(photos)
//
//    val photographersCompact: String
//        get() = format(photographers)

    val searchSubtitle: String
        get() = "${format(photos)} photos created by ${format(photographers)} photographers"
}
