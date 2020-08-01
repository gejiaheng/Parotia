package com.melodie.parotia.api

import com.melodie.parotia.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader(
            "Authorization", authorization()
        ).build()
        return chain.proceed(request)
    }

    private fun authorization(): String {
        return if (TokenLiveData.value.isNullOrEmpty()) {
            "Client-ID ${BuildConfig.CLIENT_ID}"
        } else {
            "Bearer ${TokenLiveData.value}"
        }
    }
}
