package com.kiral.charityapp.domain.util

import com.kiral.charityapp.domain.fake.responses.FakeCharityResponse
import com.kiral.charityapp.domain.fake.responses.FakeProjectList
import com.kiral.charityapp.domain.model.Charity

class CharitiesMapper: Mapper<FakeCharityResponse, Charity>{

    override fun mapToDomainModel(model: FakeCharityResponse): Charity {
        return Charity(
            id = model.id,
            name = model.name,
            imgSrc = model.imgSrc,
            address = model.address,
            description = model.description,
            raised = model.raised.toFloat(),
            peopleDonated = model.peopleDonated,
            donorDonated = model.donorDonated,
            projects =  model.projects.map { m -> m.name }

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
            donorDonated = domainModel.donorDonated,
            projects =  domainModel.projects.map { FakeProjectList(0, it) }
        )
    }

    fun mapToDomainModelList(lst: List<FakeCharityResponse>): List<Charity>{
        return lst.map { r -> mapToDomainModel(r) }
    }

}