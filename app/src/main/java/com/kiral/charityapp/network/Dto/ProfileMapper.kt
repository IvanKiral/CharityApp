package com.kiral.charityapp.network.Dto

import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.domain.util.Mapper

class ProfileMapper: Mapper<ProfileDto, Profile>{
    override fun mapToDomainModel(model: ProfileDto): Profile {
        return Profile(
            id = model.id,
            name = model.name,
            email = model.email,
            donations = model.donations,
            credit = model.credit,
            region = model.region,
            charities = "",
            automaticDonations = model.repeatActive,
            automaticDonationTimeFrequency = model.repeatFrequency,
            automaticDonationsValue = model.repeatValue,
            badges = listOf()
        )
    }

    override fun mapFromDomainModel(domainModel: Profile): ProfileDto {
        return ProfileDto(
            id = domainModel.id!!,
            name = domainModel.name,
            email = domainModel.email,
            donations = domainModel.donations,
            region = domainModel.region,
            credit = domainModel.credit,
            repeatActive = domainModel.automaticDonations,
            repeatFrequency = domainModel.automaticDonationTimeFrequency,
            repeatValue = domainModel.automaticDonationsValue,
        )
    }
}