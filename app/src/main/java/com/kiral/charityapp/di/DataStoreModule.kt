package com.kiral.charityapp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import com.kiral.charityapp.ui.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule{

    @Singleton
    @Provides
    fun provideDataStore(app: BaseApplication): DataStore<Preferences> {
        return app.createDataStore(
            name = "settings"
        )
    }
}