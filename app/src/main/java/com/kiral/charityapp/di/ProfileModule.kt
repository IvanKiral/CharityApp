package com.kiral.charityapp.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {


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