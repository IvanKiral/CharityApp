package com.kiral.charityapp.domain.util

import com.kiral.charityapp.domain.fake.responses.FakeCharityResponse
import com.kiral.charityapp.domain.fake.responses.FakeProjectList
import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.domain.model.CharityProject

class CharitiesMapper: Mapper<FakeCharityResponse, Charity>{
    override fun mapToDomainModel(model: FakeCharityResponse): Charity {
        return Charity(
            id = model.id,
            name = model.name,
            imgSrc = model.imgSrc,
            address = model.address,
            description = model.description,
            howDonationHelps = model.description,
            whyToDonate = model.description,
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

    override fun mapFromDomainModel(domainModel: Charity): FakeCharityResponse {
        return FakeCharityResponse(
            id = domainModel.id,
            name = domainModel.name,
            imgSrc = domainModel.imgSrc,
            address = domainModel.address,
            region = "svk",
            description = domainModel.description,
            raised = domainModel.raised.toDouble(),
            peopleDonated = domainModel.peopleDonated,
            //donorId = domainModel.donorId,
            donorDonated = domainModel.donorDonated,
            projects =  domainModel.projects.map { FakeProjectList(it.id, it.name) }
        )
    }

    fun mapToDomainModelList(lst: List<FakeCharityResponse>): List<Charity>{
        return lst.map { r -> mapToDomainModel(r) }
    }

}