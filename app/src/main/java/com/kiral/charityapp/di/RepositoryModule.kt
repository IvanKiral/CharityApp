package com.kiral.charityapp.di

import com.kiral.charityapp.network.CharityService
import com.kiral.charityapp.network.mappers.CharityListItemMapper
import com.kiral.charityapp.network.mappers.CharityMapper
import com.kiral.charityapp.network.mappers.DonorsMapper
import com.kiral.charityapp.network.mappers.LeaderboardMapper
import com.kiral.charityapp.network.mappers.ProjectMapper
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
        charityGoalMapper: ProjectMapper,
        charityListItemMapper: CharityListItemMapper,
        donorsMapper: DonorsMapper,
        leaderboardMapper: LeaderboardMapper,
        networkService: CharityService
    ): CharityRepository {
        return CharityRepositoryImpl(
            charityMapper = charityMapper,
            charityGoalMapper = charityGoalMapper,
            charityListMapper = charityListItemMapper,
            donorsMapper = donorsMapper,
            leaderboardMapper = leaderboardMapper,
            networkService = networkService
        )
    }
}