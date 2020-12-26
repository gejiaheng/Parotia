package com.melodie.parotia.model

data class UserStats(
    val username: String,
    val downloads: Data,
    val views: Data,
    val likes: Data,
)

data class Data(
    val total: Int,
    val historical: Historical,
)

data class Historical(
    val change: Int,
    val average: Int,
    val resolution: String,
    val quantity: Int,
    val values: List<Value>,
)

data class Value(
    val date: String,
    val value: Int,
)
