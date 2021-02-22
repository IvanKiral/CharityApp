package com.kiral.charityapp.di

import com.kiral.charityapp.domain.fake.FakeDatabase
import com.kiral.charityapp.domain.util.CharitiesMapper
import com.kiral.charityapp.domain.util.ProfileMapper
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
    fun provideProfileMapper(): ProfileMapper {
        return ProfileMapper()
    }

    @Singleton
    @Provides
    fun provideProfileRepository(
        profileMapper: ProfileMapper,
        fakeDatabase: FakeDatabase
    ): ProfileRepository{
        return ProfileRepositoryImpl(
            profileMapper = profileMapper,
            fakeDatabase = fakeDatabase
        )
    }
}