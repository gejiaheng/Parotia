package com.melodie.parotia.ui.profile

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
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
import com.melodie.parotia.model.UserStats
import com.melodie.parotia.result.data
import kotlinx.coroutines.launch

class ProfileViewModel @ViewModelInject constructor(
    private val preference: SharedPreferenceStorage,
    private val oauthUseCase: OAuthUseCase,
    private val getMeUseCase: GetMeUseCase,
    private val getUserStatsUserCase: GetUserStatisticsUseCase
) : ViewModel() {

    val loggedIn: LiveData<Boolean> = map(TokenLiveData) { !it.isNullOrEmpty() }
    private val _me: MutableLiveData<User> = MutableLiveData()
    val me: LiveData<User> = _me
    private val _userStats: MutableLiveData<UserStats> = MutableLiveData()
    val userStats: LiveData<UserStats> = _userStats
    val username: LiveData<String> = map(me) { it?.username }

    private val observer = Observer<Boolean> {
        if (it) {
            viewModelScope.launch {
                val user = getMeUseCase(Unit).data
                _me.value = user

                val userStats = getUserStatsUserCase(user!!.username).data
                _userStats.value = userStats
            }
        } else {
            _me.value = null
            _userStats.value = null
        }
    }

    init {
        loggedIn.observeForever(observer)
    }

    override fun onCleared() {
        super.onCleared()
        loggedIn.removeObserver(observer)
    }

    fun startOauth(context: Context) {
        val url =
            "https://unsplash.com/oauth/authorize?client_id=${BuildConfig.CLIENT_ID}&redirect_uri=$REDIRECT_URI&response_type=code&scope=$SCOPE"
        val builder = CustomTabsIntent.Builder()
        val intent = builder.build()
        intent.launchUrl(context, Uri.parse(url))
    }

    fun oauth(code: String) {
        viewModelScope.launch {
            val token = oauthUseCase(code).data?.access_token
            TokenLiveData.value = token
            preference.token = token
        }
    }
}
