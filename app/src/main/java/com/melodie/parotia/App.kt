package com.melodie.parotia

import android.app.Application
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.soloader.SoLoader
import com.melodie.parotia.api.TokenLiveData
import com.melodie.parotia.data.prefs.SharedPreferenceStorage
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var preferences: SharedPreferenceStorage
    // TODO("only inject in debug build")
    // FIXME("not working, crash when start up")
//    @Inject
//    lateinit var flipperClient: FlipperClient

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, false)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
//            flipperClient.start()
        }

        val token = preferences.token
        if (!token.isNullOrEmpty()) {
            TokenLiveData.value = token
        }
    }
}
