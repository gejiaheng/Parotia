package com.melodie.parotia.data.search

import com.google.gson.Gson
import com.melodie.parotia.data.prefs.SharedPreferenceStorage
import javax.inject.Inject

class SearchHistoryRepository @Inject constructor(
    private val preferences: SharedPreferenceStorage,
    private val gson: Gson
) {
    fun getHistory(): List<String> {
        val historyArr = gson.fromJson(preferences.searchHistory, Array<String>::class.java)
        return historyArr?.toList() ?: emptyList()
    }

    fun saveHistory(history: List<String>) {
        preferences.searchHistory = gson.toJson(history)
    }
}
