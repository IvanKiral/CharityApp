package com.kiral.charityapp.repositories.charities

import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.domain.profile

class ProfileRepositoryImpl: ProfileRepository {
    override fun getProfile(): Profile {
        return profile
    }
}