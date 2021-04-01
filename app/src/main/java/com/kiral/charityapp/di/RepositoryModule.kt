package com.kiral.charityapp.di

import com.kiral.charityapp.network.mappers.CharityListItemMapper
import com.kiral.charityapp.network.mappers.CharityMapper
import com.kiral.charityapp.network.mappers.DonorsMapper
import com.kiral.charityapp.network.mappers.LeaderboardMapper
import com.kiral.charityapp.network.mappers.ProfileMapper
import com.kiral.charityapp.network.mappers.ProjectMapper
import com.kiral.charityapp.network.services.CharityService
import com.kiral.charityapp.network.services.ProfileService
import com.kiral.charityapp.repositories.charities.CharityRepository
import com.kiral.charityapp.repositories.charities.CharityRepositoryImpl
import com.kiral.charityapp.repositories.profile.ProfileRepository
import com.kiral.charityapp.repositories.profile.ProfileRepositoryImpl
import com.kiral.charityapp.ui.BaseApplication
import com.kiral.charityapp.utils.AssetProvider
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
    fun provideAssetProvider(
        context: BaseApplication
    ): AssetProvider{
        return AssetProvider(context)
    }

    @Singleton
    @Provides
    fun provideCharityRepository(
        charityMapper: CharityMapper,
        charityGoalMapper: ProjectMapper,
        charityListItemMapper: CharityListItemMapper,
        donorsMapper: DonorsMapper,
        leaderboardMapper: LeaderboardMapper,
        networkService: CharityService,
        assetProvider: AssetProvider
    ): CharityRepository {
        return CharityRepositoryImpl(
            charityMapper = charityMapper,
            charityGoalMapper = charityGoalMapper,
            charityListMapper = charityListItemMapper,
            donorsMapper = donorsMapper,
            leaderboardMapper = leaderboardMapper,
            networkService = networkService,
            assetProvider = assetProvider
        )
    }

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
        assetProvider: AssetProvider
    ): ProfileRepository {
        return ProfileRepositoryImpl(
            profileService = profileService,
            profileMapper = profileMapper,
            assetProvider = assetProvider
        )
    }

}