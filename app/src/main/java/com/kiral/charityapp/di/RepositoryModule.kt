package com.kiral.charityapp.di

import com.kiral.charityapp.repositories.charities.CharityRepository
import com.kiral.charityapp.repositories.charities.CharityRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule{

    @Singleton
    @Provides
    fun provideCharityRepository(): CharityRepository{
        return CharityRepositoryImpl()
    }
}