package com.kiral.charityapp.di

import com.google.gson.GsonBuilder
import com.kiral.charityapp.network.Dto.CharityGoalMapper

import com.kiral.charityapp.network.Dto.CharityListItemMapper
import com.kiral.charityapp.network.Dto.CharityMapper
import com.kiral.charityapp.network.Dto.DonorsMapper
import com.kiral.charityapp.network.NetworkService
import com.kiral.charityapp.utils.PrivateConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

val address = PrivateConstants.address

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{

    @Singleton
    @Provides
    fun provideCharityListItemMapper(): CharityListItemMapper {
        return CharityListItemMapper()
    }

    @Singleton
    @Provides
    fun provideCharityMapper(): CharityMapper {
        return CharityMapper()
    }

    @Singleton
    @Provides
    fun provideCharityGoalMapper(): CharityGoalMapper {
        return CharityGoalMapper()
    }

    @Singleton
    @Provides
    fun provideDonorsMapper(): DonorsMapper {
        return DonorsMapper()
    }

    @Singleton
    @Provides
    fun provideNetworkService(): NetworkService {
        return Retrofit.Builder()
            .baseUrl(address)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(NetworkService::class.java)
    }
}