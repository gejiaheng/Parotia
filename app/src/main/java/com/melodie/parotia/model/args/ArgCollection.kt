package com.melodie.parotia.model.args

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArgCollection(
    val id: Long,
    val title: String,
    val description: String?,
    val coverUrl: String
) : Parcelable
