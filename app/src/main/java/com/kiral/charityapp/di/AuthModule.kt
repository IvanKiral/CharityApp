package com.kiral.charityapp.di

import com.auth0.android.Auth0
import com.kiral.charityapp.ui.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule{

    @Singleton
    @Provides
    fun provideAuth(app: BaseApplication): Auth0 {
        return Auth0(app.baseContext)
    }
}