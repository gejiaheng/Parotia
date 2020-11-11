package com.melodie.parotia

import android.app.Application
import com.melodie.parotia.api.TokenLiveData
import com.melodie.parotia.data.prefs.SharedPreferenceStorage
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var preferences: SharedPreferenceStorage

    override fun onCreate() {
        super.onCreate()
        TokenLiveData.value = preferences.token
    }
}
