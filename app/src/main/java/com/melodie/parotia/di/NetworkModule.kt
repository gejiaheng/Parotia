package com.melodie.parotia.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.melodie.parotia.api.AuthInterceptor
import com.melodie.parotia.api.BASE_URL
import com.melodie.parotia.api.service.AuthService
import com.melodie.parotia.api.service.CollectionService
import com.melodie.parotia.api.service.PhotoService
import com.melodie.parotia.api.service.SearchService
import com.melodie.parotia.api.service.StatsService
import com.melodie.parotia.api.service.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesRetrofit(
        authInterceptor: AuthInterceptor,
        gson: Gson
    ): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY).redactHeader("Authorization")
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return GsonBuilder()
            .setPrettyPrinting()
//            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()
    }

    @Provides
    @Singleton
    fun providesAuthService(
        retrofit: Retrofit
    ): AuthService = retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun providesPhotoService(
        retrofit: Retrofit
    ): PhotoService = retrofit.create(PhotoService::class.java)

    @Provides
    @Singleton
    fun providesCollectionService(
        retrofit: Retrofit
    ): CollectionService = retrofit.create(CollectionService::class.java)

    @Provides
    @Singleton
    fun providesSearchService(
        retrofit: Retrofit
    ): SearchService = retrofit.create(SearchService::class.java)

    @Provides
    @Singleton
    fun providesUserService(
        retrofit: Retrofit
    ): UserService = retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun providesStatsService(
        retrofit: Retrofit
    ): StatsService = retrofit.create(StatsService::class.java)
}
