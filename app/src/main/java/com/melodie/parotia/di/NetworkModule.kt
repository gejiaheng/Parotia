package com.melodie.parotia.di

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesFlipperClient(
        context: Application,
        networkFlipperPlugin: NetworkFlipperPlugin
    ): FlipperClient {
        val client = AndroidFlipperClient.getInstance(context)
        client.addPlugin(networkFlipperPlugin)
        client.addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
        return client
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        authInterceptor: AuthInterceptor,
        flipperOkhttpInterceptor: FlipperOkhttpInterceptor
    ): Retrofit {
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addNetworkInterceptor(flipperOkhttpInterceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun providesFlipperOkhttpInterceptor(
        networkFlipperPlugin: NetworkFlipperPlugin
    ): FlipperOkhttpInterceptor = FlipperOkhttpInterceptor(networkFlipperPlugin)

    @Provides
    @Singleton
    fun providesNetworkFlipperPlugin(): NetworkFlipperPlugin = NetworkFlipperPlugin()

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
