package com.kiral.charityapp.repositories.charities

import com.kiral.charityapp.domain.model.Profile

interface ProfileRepository{
    fun login(email: String): Profile?

    fun register(profile: Profile): Boolean

    fun getProfile(id: Int): Profile
}