package com.kiral.charityapp.di

import com.kiral.charityapp.network.Dto.CharityGoalMapper
import com.kiral.charityapp.network.Dto.CharityListItemMapper
import com.kiral.charityapp.network.Dto.CharityMapper
import com.kiral.charityapp.network.Dto.DonorsMapper
import com.kiral.charityapp.network.NetworkService
import com.kiral.charityapp.repositories.charities.CharityRepository
import com.kiral.charityapp.repositories.charities.CharityRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideCharityRepository(
        charityMapper: CharityMapper,
        charityGoalMapper: CharityGoalMapper,
        charityListItemMapper: CharityListItemMapper,
        donorsMapper: DonorsMapper,
        networkService: NetworkService
    ): CharityRepository {
        return CharityRepositoryImpl(
            charityMapper = charityMapper,
            charityGoalMapper = charityGoalMapper,
            charityListMapper = charityListItemMapper,
            donorsMapper = donorsMapper,
            networkService = networkService
        )
    }
}