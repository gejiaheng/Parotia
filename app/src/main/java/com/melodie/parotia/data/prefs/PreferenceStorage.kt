package com.melodie.parotia.data.prefs

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

interface PreferenceStorage {
    var token: String?
    var searchHistory: String?
    var searchBanners: String?
}

@Singleton
class SharedPreferenceStorage @Inject constructor(
    @ApplicationContext context: Context
) : PreferenceStorage {

    private val prefs: Lazy<SharedPreferences> = lazy {
        context.applicationContext.getSharedPreferences(
            PREFS_NAME, MODE_PRIVATE
        )
    }

    // TODO("maybe change to LiveData<String>")
    override var token by StringPreference(
        prefs, PREF_TOKEN, null
    )

    override var searchHistory by StringPreference(
        prefs, PREF_SEARCH_HISTORY, null
    )

    override var searchBanners by StringPreference(
        prefs, PREF_SEARCH_BANNERS, null
    )

    companion object {
        const val PREFS_NAME = "unsplash"
        const val PREF_TOKEN = "pref_token"
        const val PREF_SEARCH_HISTORY = "pref_search_history"
        const val PREF_SEARCH_BANNERS = "pref_search_banners"
    }
}

class StringPreference(
    private val preferences: Lazy<SharedPreferences>,
    private val name: String,
    private val defaultValue: String?
) : ReadWriteProperty<Any, String?> {

    override fun getValue(thisRef: Any, property: KProperty<*>): String? {
        return preferences.value.getString(name, defaultValue) ?: defaultValue
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) {
        preferences.value.edit { putString(name, value) }
    }
}
