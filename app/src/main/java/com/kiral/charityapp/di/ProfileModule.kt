package com.kiral.charityapp.di

import com.kiral.charityapp.repositories.charities.ProfileRepository
import com.kiral.charityapp.repositories.charities.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProfileModule{
    @Singleton
    @Provides
    fun provideProfileRepository(): ProfileRepository{
        return ProfileRepositoryImpl()
    }
}