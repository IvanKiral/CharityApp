package com.kiral.charityapp.repositories.charities

import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.domain.profiles

class ProfileRepositoryImpl: ProfileRepository {
    override fun login(email: String): Profile? {
        return profiles.filter { profile -> profile.email == email }.firstOrNull()
    }

    override fun register(profile: Profile): Boolean {
        profiles.add(profile)
        return true
    }

    override fun getProfile(email: String): Profile {
        return profiles.filter { p -> p.email == email }.first()
    }
}