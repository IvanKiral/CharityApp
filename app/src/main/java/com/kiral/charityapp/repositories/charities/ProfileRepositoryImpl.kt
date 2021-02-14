package com.kiral.charityapp.repositories.charities

import com.kiral.charityapp.domain.fake.*
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
        val profileId = fakeProfiles.last().id + 1
        fakeProfiles.add(
            FakeProfile(
                id = profileId,
                name = profile.name,
                region = "svk",
                credit = 0f,
                email = profile.email
            )
        )
        fakeDonationRepeats.add(
            FakeDonationRepeat(
                id = fakeDonationRepeats.last().id + 1,
                donorId = profileId,
                active = profile.automaticDonations,
                sum = profile.automaticDonationsValue,
                repeatingStatus = profile.automaticDonationTimeFrequency
            )
        )
        return true
    }

    override fun getProfile(email: String): Profile {
        val x = fakeProfiles.filter { p -> p.email == email }.first()
        val donationRepeat = fakeDonationRepeats.filter { d -> d.donorId == x.id }.first()
        return  if(x == null) x
        else Profile(
            id = x.id,
            email = x.email,
            name = x.name,
            donations = fakeDonations.filter{ d -> d.donorId == x.id }.size,
            credit = x.credit,
            charities = "",
            automaticDonationsValue = donationRepeat.sum,
            automaticDonationTimeFrequency = donationRepeat.repeatingStatus,
            automaticDonations = donationRepeat.active,
            badges = listOf()
        )
    }
}