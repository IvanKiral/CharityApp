package com.kiral.charityapp.repositories.charities

import com.kiral.charityapp.domain.model.Badge
import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.network.DataState
import kotlinx.coroutines.flow.Flow

interface ProfileRepository{
    fun login(email: String): Flow<DataState<Int>>

    fun register(profile: Profile): Flow<DataState<Boolean>>

    fun getProfile(id: Int): Flow<DataState<Profile>>

    fun updateRegularDonation(
        id: Int,
        regularDonationActive: Boolean,
        regularDonationValue: Double?,
        regularDonationFrequency: Int?
    ): Flow<DataState<Boolean>>

    fun updateRegularDonationActive(
        id: Int,
        regularDonationActive: Boolean,
    ): Flow<DataState<Boolean>>

    fun updateCategories(
        id: Int,
        categories: List<Int>,
    ): Flow<DataState<Boolean>>

    fun updateRegion(
        id: Int,
        region: String,
    ): Flow<DataState<Boolean>>

    fun addCredit(
        id: Int,
        credit: Double,
    ): Flow<DataState<Boolean>>

    fun getBadges(donorId: Int): List<Badge>
}