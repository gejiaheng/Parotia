package com.melodie.parotia.api.service

import com.melodie.parotia.BuildConfig
import com.melodie.parotia.model.AccessToken
import retrofit2.http.POST
import retrofit2.http.Query

const val SCOPE = "public"
const val REDIRECT_URI = "x-arch-oauth-unsplash://callback"
const val GRANT_TYPE = "authorization_code"

interface AuthService {
    @POST("https://unsplash.com/oauth/token")
    suspend fun getToken(
        @Query("client_id") clientId: String = BuildConfig.CLIENT_ID,
        @Query("client_secret") clientSecret: String = BuildConfig.CLIENT_SECRET,
        @Query("redirect_uri") redirectUri: String = REDIRECT_URI,
        @Query("code") code: String,
        @Query("grant_type") grantType: String = GRANT_TYPE
    ): AccessToken
}
