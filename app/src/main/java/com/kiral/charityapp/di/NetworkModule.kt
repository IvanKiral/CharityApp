package com.kiral.charityapp.di

import com.google.gson.GsonBuilder
import com.kiral.charityapp.network.mappers.CharityListItemMapper
import com.kiral.charityapp.network.mappers.CharityMapper
import com.kiral.charityapp.network.mappers.DonorsMapper
import com.kiral.charityapp.network.mappers.LeaderboardMapper
import com.kiral.charityapp.network.mappers.ProjectMapper
import com.kiral.charityapp.network.services.CharityService
import com.kiral.charityapp.network.services.ProfileService
import com.kiral.charityapp.utils.PrivateConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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
    fun provideCharityGoalMapper(): ProjectMapper {
        return ProjectMapper()
    }

    @Singleton
    @Provides
    fun provideDonorsMapper(): DonorsMapper {
        return DonorsMapper()
    }

    @Singleton
    @Provides
    fun provideLeaderboardMapper(): LeaderboardMapper {
        return LeaderboardMapper()
    }

    @Singleton
    @Provides
    fun provideNetworkService(): CharityService {
        return Retrofit.Builder()
            .baseUrl(address)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(OkHttpClient()
                .newBuilder()
                .callTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor()
                    .setLevel(
                HttpLoggingInterceptor.Level.BODY))
                .build())
            .build()
            .create(CharityService::class.java)
    }

    @Singleton
    @Provides
    fun provideProfileService(): ProfileService {
        return Retrofit.Builder()
            .baseUrl(address)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setDateFormat("yyyy-MM-dd").create()))
            .client(OkHttpClient()
                .newBuilder()
                .callTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build())
            .build()
            .create(ProfileService::class.java)
    }
}