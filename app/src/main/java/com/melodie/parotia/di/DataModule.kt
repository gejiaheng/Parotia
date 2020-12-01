package com.melodie.parotia.di

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.melodie.parotia.data.prefs.DEFAULT_PREF_FILE
import com.melodie.parotia.data.prefs.PREF_KEY_TOKEN
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesPreferences(
        ctx: Application
    ): DataStore<Preferences> = ctx.createDataStore(
        name = DEFAULT_PREF_FILE
    )

    @Provides
    @Singleton
    @Named(PREF_KEY_TOKEN)
    fun providesTokenKey(): Preferences.Key<String> = preferencesKey(PREF_KEY_TOKEN)
}
