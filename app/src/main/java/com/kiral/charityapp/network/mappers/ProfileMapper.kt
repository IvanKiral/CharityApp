package com.kiral.charityapp.network.mappers

import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.network.dtos.ProfileDto

class ProfileMapper: Mapper<ProfileDto, Profile> {
    override fun mapToDomainModel(model: ProfileDto): Profile {
        return Profile(
            id = model.id,
            name = model.name,
            email = model.email,
            donations = model.donations,
            credit = model.credit,
            region = model.region,
            categories = model.categories ,
            regularDonationActive = model.repeatActive,
            regularDonationFrequency = model.repeatFrequency,
            regularDonationValue = model.repeatValue,
            badges = model.badges
        )
    }

    fun mapFromDomainModel(domainModel: Profile): ProfileDto {
        return ProfileDto(
            id = domainModel.id,
            name = domainModel.name,
            email = domainModel.email,
            donations = domainModel.donations,
            region = domainModel.region,
            credit = domainModel.credit,
            categories = domainModel.categories,
            repeatActive = domainModel.regularDonationActive,
            repeatFrequency = domainModel.regularDonationFrequency,
            repeatValue = domainModel.regularDonationValue,
            badges = domainModel.badges
        )
    }

}