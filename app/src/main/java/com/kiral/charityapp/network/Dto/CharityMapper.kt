package com.kiral.charityapp.network.Dto

import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.domain.model.CharityProject
import com.kiral.charityapp.domain.util.Mapper

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
            raised = model.raised.toFloat(),
            peopleDonated = model.peopleDonated,
            donorDonated = model.donorDonated,
            //donorId = model.donorId,
            projects =  model.projects.map { CharityProject(
                id = it.id,
                name = it.name
            ) }
        )
    }

    override fun mapFromDomainModel(domainModel: Charity): CharityDto {
        return CharityDto(
            id = domainModel.id,
            name = domainModel.name,
            imgSrc = domainModel.imgSrc,
            address = domainModel.address,
            charityStory = domainModel.description,
            howDonationHelps = domainModel.howDonationHelps,
            whyToDonate = domainModel.whyToDonate,
            raised = domainModel.raised.toDouble(),
            peopleDonated = domainModel.peopleDonated,
            //donorId = domainModel.donorId,
            donorDonated = domainModel.donorDonated,
            projects =  domainModel.projects.map { ProjectListItemDto(it.id, it.name) }
        )
    }
}