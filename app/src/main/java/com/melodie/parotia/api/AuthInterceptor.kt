package com.melodie.parotia.api

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.melodie.parotia.BuildConfig
import com.melodie.parotia.data.prefs.PREF_KEY_TOKEN
import kotlinx.coroutines.flow.map
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Named

class AuthInterceptor @Inject constructor(
    private val preferences: DataStore<Preferences>,
    @Named(PREF_KEY_TOKEN) private val KEY_TOKEN: Preferences.Key<String>,
) : Interceptor {

    private val token: LiveData<String> = preferences.data
        .map { preferences -> preferences[KEY_TOKEN] ?: "" }
        .asLiveData()

    init {
        token.observeForever {
            tokenValue = it
        }
    }

    var tokenValue: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader(
            "Authorization", authorization()
        ).build()
        return chain.proceed(request)
    }

    private fun authorization(): String {
        return if (tokenValue.isNullOrEmpty()) {
            "Client-ID ${BuildConfig.CLIENT_ID}"
        } else {
            "Bearer $tokenValue"
        }
    }
}
