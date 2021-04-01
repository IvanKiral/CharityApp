package com.kiral.charityapp.network.mappers

import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.domain.model.CharityProject
import com.kiral.charityapp.network.dtos.CharityDto

class CharityMapper: Mapper<CharityDto, Charity> {
    override fun mapToDomainModel(model: CharityDto): Charity {
        return Charity(
            id = model.id,
            name = model.name,
            imgSrc = model.imgSrc,
            address = model.address,
            description = model.charityStory,
            howDonationHelps = model.howDonationHelps,
            whyToDonate = model.whyToDonate,
            raised = model.raised,
            peopleDonated = model.peopleDonated,
            donorDonated = model.donorDonated,
            projects =  model.projects.map { CharityProject(
                id = it.id,
                name = it.name
            ) }
        )
    }
}