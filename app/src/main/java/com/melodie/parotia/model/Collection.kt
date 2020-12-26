package com.melodie.parotia.model

import com.melodie.parotia.model.args.ArgCollection

data class Collection(
    val id: Long,
    val title: String,
    val description: String,
    val published_at: String,
    val last_collected_at: String,
    val updated_at: String,
    val featured: Boolean,
    val total_photos: Int,
    val private: Boolean,
    val share_key: String,
    val cover_photo: Photo,
    val user: User,
    val links: CLinks,
) {
    fun shrink(): ArgCollection {
        return ArgCollection(
            id,
            title,
            description,
            user.profile_image.medium,
            user.name
        )
    }
}

data class CLinks(
    val self: String,
    val html: String,
    val photos: String
)
