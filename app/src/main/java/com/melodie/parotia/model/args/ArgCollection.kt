package com.melodie.parotia.model.args

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Argument model object passed to [com.melodie.parotia.ui.collection.detail.CollectionFragment].
 */
@Parcelize
data class ArgCollection(
    val id: Long,
    val title: String,
    val description: String?,
    val userAvatar: String,
    val userName: String,
) : Parcelable
