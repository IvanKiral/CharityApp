package com.kiral.charityapp.repositories.charities

import com.kiral.charityapp.domain.model.Profile

interface ProfileRepository{
    fun getProfile(): Profile
}