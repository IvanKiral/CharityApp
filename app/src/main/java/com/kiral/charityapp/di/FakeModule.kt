package com.kiral.charityapp.di

import com.kiral.charityapp.domain.fake.FakeDatabase
import com.kiral.charityapp.domain.util.CharitiesMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FakeModule{

    @Singleton
    @Provides
    fun provideFakeDatabase(): FakeDatabase {
        return FakeDatabase()
    }
}