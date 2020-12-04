package com.melodie.parotia.ui.account

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.melodie.parotia.BuildConfig
import com.melodie.parotia.api.service.REDIRECT_URI
import com.melodie.parotia.api.service.SCOPE
import com.melodie.parotia.data.prefs.PREF_KEY_TOKEN
import com.melodie.parotia.domain.account.OAuthUseCase
import com.melodie.parotia.domain.user.GetMeUseCase
import com.melodie.parotia.domain.user.GetUserStatisticsUseCase
import com.melodie.parotia.model.User
import com.melodie.parotia.result.Result
import com.melodie.parotia.result.data
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Named

// Should be scoped to activity
class AccountViewModel @ViewModelInject constructor(
    private val preferences: DataStore<Preferences>,
    @Named(PREF_KEY_TOKEN) private val KEY_TOKEN: Preferences.Key<String>,
    private val oauthUseCase: OAuthUseCase,
    private val getMeUseCase: GetMeUseCase,
    private val getUserStatsUserCase: GetUserStatisticsUseCase
) : ViewModel() {

    private val token: LiveData<String> = preferences.data
        .map { preferences -> preferences[KEY_TOKEN] ?: "" }
        .asLiveData()

    val loggedIn: LiveData<Boolean> = map(token) {
        !it.isNullOrEmpty()
    }

    val me: LiveData<Result<User>> = switchMap(loggedIn) {
        liveData {
            if (it) {
                getMeUseCase(Unit).data!!.asLiveData().apply {
                    emitSource(this)
                }
            }
            // TODO("logout")
//            else {
//                emit(null)
//            }
        }
    }

    fun authorize(context: Context) {
        val url =
            "https://unsplash.com/oauth/authorize?client_id=${BuildConfig.CLIENT_ID}&redirect_uri=$REDIRECT_URI&response_type=code&scope=$SCOPE"
        val builder = CustomTabsIntent.Builder()
        val intent = builder.build()
        intent.launchUrl(context, Uri.parse(url))
    }

    fun getToken(code: String) {
        viewModelScope.launch {
            oauthUseCase(code).data?.access_token?.apply {
                preferences.edit { pref ->
                    pref[KEY_TOKEN] = this
                }
            }
        }
    }
}
