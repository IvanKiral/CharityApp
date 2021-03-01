package com.kiral.charityapp.di

import com.kiral.charityapp.network.Dto.ProfileMapper
import com.kiral.charityapp.network.ProfileService
import com.kiral.charityapp.repositories.charities.ProfileRepository
import com.kiral.charityapp.repositories.charities.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Singleton
    @Provides
    fun provideProfileMapper(): ProfileMapper {
        return ProfileMapper()
    }

    @Singleton
    @Provides
    fun provideProfileRepository(
        profileService: ProfileService,
        profileMapper: ProfileMapper,
    ): ProfileRepository {
        return ProfileRepositoryImpl(
            profileService = profileService,
            profileMapper = profileMapper
        )
    }

/*@Singleton
@Provides
fun provideProfileRepository(
    profileMapper: ProfileMapper,
    fakeDatabase: FakeDatabase
): ProfileRepository{
    return FakeProfileRepositoryImpl(
        profileMapper = profileMapper,
        fakeDatabase = fakeDatabase
    )
}*/
}