package com.melodie.parotia.model

data class SearchResult<T>(
    val total: Int,
    val total_pages: Int,
    val results: List<T>,
)
