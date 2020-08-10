package com.melodie.parotia.ui.account

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.melodie.parotia.BuildConfig
import com.melodie.parotia.api.TokenLiveData
import com.melodie.parotia.api.service.REDIRECT_URI
import com.melodie.parotia.api.service.SCOPE
import com.melodie.parotia.data.prefs.SharedPreferenceStorage
import com.melodie.parotia.domain.account.OAuthUseCase
import com.melodie.parotia.domain.user.GetMeUseCase
import com.melodie.parotia.domain.user.GetUserStatisticsUseCase
import com.melodie.parotia.model.User
import com.melodie.parotia.result.data
import kotlinx.coroutines.launch

// Should be scoped to activity
class AccountViewModel @ViewModelInject constructor(
    private val preference: SharedPreferenceStorage,
    private val oauthUseCase: OAuthUseCase,
    private val getMeUseCase: GetMeUseCase,
    private val getUserStatsUserCase: GetUserStatisticsUseCase
) : ViewModel() {

    val loggedIn: LiveData<Boolean> = map(TokenLiveData) { !it.isNullOrEmpty() }

    val me: LiveData<User?> = switchMap(loggedIn) {
        liveData<User?> {
            if (it) {
                getMeUseCase(Unit).data?.apply {
                    emitSource(this)
                }
            } else {
                emit(null)
            }
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
                TokenLiveData.value = this
                preference.token = this
            }
        }
    }
}
