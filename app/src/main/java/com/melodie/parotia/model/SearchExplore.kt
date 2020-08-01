package com.melodie.parotia.model

import androidx.annotation.DrawableRes

data class SearchExplore(
    val title: String,
    @DrawableRes val imageRes: Int?,
    val imageUrl: String?
)
