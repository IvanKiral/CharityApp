package com.kiral.charityapp.di

import com.kiral.charityapp.domain.fake.FakeDatabase
import com.kiral.charityapp.domain.util.CharitiesMapper
import com.kiral.charityapp.domain.util.CharityListItemMapper
import com.kiral.charityapp.domain.util.DonorsMapper
import com.kiral.charityapp.domain.util.ProjectMapper
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
    fun provideCharityListMapper(): CharityListItemMapper {
        return CharityListItemMapper()
    }

    @Singleton
    @Provides
    fun provideProjectMapper(): ProjectMapper {
        return ProjectMapper()
    }

    @Singleton
    @Provides
    fun provideDonorsMapper(): DonorsMapper {
        return DonorsMapper()
    }

    @Singleton
    @Provides
    fun provideCharityRepository(
        charityMapper: CharitiesMapper,
        projectMapper: ProjectMapper,
        charityListItemMapper: CharityListItemMapper,
        donorsMapper: DonorsMapper,
        fakeDatabase: FakeDatabase
    ): CharityRepository{
        return CharityRepositoryImpl(
            charityMapper = charityMapper,
            projectMapper = projectMapper,
            charityListMapper = charityListItemMapper,
            donorsMapper = donorsMapper,
            fakeDatabse = fakeDatabase
        )
    }


}