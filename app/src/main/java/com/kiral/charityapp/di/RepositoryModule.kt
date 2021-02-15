package com.kiral.charityapp.di

import com.kiral.charityapp.domain.fake.FakeDatabase
import com.kiral.charityapp.domain.util.CharitiesMapper
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
    fun provideCharitiesMapper(): CharitiesMapper {
        return CharitiesMapper()
    }

    @Singleton
    @Provides
    fun provideCharityRepository(
        charityMapper: CharitiesMapper,
        fakeDatabase: FakeDatabase
    ): CharityRepository{
        return CharityRepositoryImpl(
            charityMapper = charityMapper,
            fakeDatabse = fakeDatabase
        )
    }


}