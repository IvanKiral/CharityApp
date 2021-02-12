package com.kiral.charityapp.repositories.charities

import com.kiral.charityapp.domain.fake.fakeDonations
import com.kiral.charityapp.domain.fake.fakeProfiles
import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.domain.profiles

class ProfileRepositoryImpl: ProfileRepository {
    override fun login(email: String): Profile? {
        val x = fakeProfiles.filter { profile -> profile.email == email }.firstOrNull()
        return  if(x == null) x
            else Profile(
            id = x.id,
            email = x.email,
            name = x.name,
            donations = fakeDonations.filter{ d -> d.donorId == x.id }.size,
            credit = x.credit,
            charities = "",
            automaticDonationsValue = 0,
            automaticDonationTimeFrequency = "day",
            automaticDonations = false,
            badges = listOf()
        )

    }

    override fun register(profile: Profile): Boolean {
        profiles.add(profile)
        return true
    }

    override fun getProfile(email: String): Profile {
        val x = fakeProfiles.filter { p -> p.email == email }.first()
        return  if(x == null) x
        else Profile(
            id = x.id,
            email = x.email,
            name = x.name,
            donations = fakeDonations.filter{ d -> d.donorId == x.id }.size,
            credit = x.credit,
            charities = "",
            automaticDonationsValue = 0,
            automaticDonationTimeFrequency = "day",
            automaticDonations = false,
            badges = listOf()
        )
    }
}