package com.kiral.charityapp.domain.util

import com.kiral.charityapp.domain.fake.responses.FakeProfilePost
import com.kiral.charityapp.domain.model.Badge
import com.kiral.charityapp.domain.model.Profile

class ProfileMapper: Mapper<FakeProfilePost, Profile>{

    override fun mapToDomainModel(model: FakeProfilePost): Profile {
        return Profile(
            id = model.id,
            name = model.name,
            email = model.email,
            donations = model.donations?.let { model.donations } ?: 0,
            credit = model.credit,
            charities = "",
            automaticDonations = model.donationRepeat,
            automaticDonationTimeFrequency = model.donationRepeatFrequency,
            automaticDonationsValue = model.donationRepeatValue,
            badges = listOf()
        )
    }

    override fun mapFromDomainModel(domainModel: Profile): FakeProfilePost {
        return FakeProfilePost(
            name = domainModel.name,
            email = domainModel.email,
            region = "svk",
            credit = domainModel.credit,
            donationRepeat = domainModel.automaticDonations,
            donationRepeatFrequency = domainModel.automaticDonationTimeFrequency,
            donationRepeatValue = domainModel.automaticDonationsValue,
            badges = listOf()
        )
    }

}