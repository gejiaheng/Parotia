package com.melodie.parotia.api

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.melodie.parotia.api.service.AuthService
import com.melodie.parotia.api.service.PhotoService
import com.melodie.parotia.api.service.UserService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val STARTING_PAGE_INDEX = 1
const val PAGE_SIZE = 40
const val BASE_URL = "https://api.unsplash.com/"

object UnsplashApi {

    private val client = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .addNetworkInterceptor(FlipperOkhttpInterceptor(NetworkFlipperPluginIns.ins))
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val photoService: PhotoService by lazy {
        retrofit.create(PhotoService::class.java)
    }

    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }

    val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }
}
