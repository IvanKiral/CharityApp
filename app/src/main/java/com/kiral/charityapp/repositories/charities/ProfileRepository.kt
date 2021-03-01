package com.kiral.charityapp.repositories.charities

import com.kiral.charityapp.domain.model.Badge
import com.kiral.charityapp.domain.model.Profile

interface ProfileRepository{
    suspend fun login(email: String): Int?

    suspend fun register(profile: Profile): Boolean

    suspend fun getProfile(id: Int): Profile

    suspend fun getBadges(donorId: Int): List<Badge>
}