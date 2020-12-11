package com.melodie.parotia.api.service

import com.melodie.parotia.BuildConfig
import com.melodie.parotia.model.AccessToken
import retrofit2.http.POST
import retrofit2.http.Query

const val SCOPE = "public"
const val REDIRECT_URI = "x-unsplash-parotia-open://callback"
const val GRANT_TYPE = "authorization_code"

interface AuthService {
    @POST("https://unsplash.com/oauth/token")
    suspend fun getToken(
        @Query("client_id") clientId: String = BuildConfig.ACCESS_KEY,
        @Query("client_secret") clientSecret: String = BuildConfig.SECRET_KEY,
        @Query("redirect_uri") redirectUri: String = REDIRECT_URI,
        @Query("code") code: String,
        @Query("grant_type") grantType: String = GRANT_TYPE
    ): AccessToken
}
