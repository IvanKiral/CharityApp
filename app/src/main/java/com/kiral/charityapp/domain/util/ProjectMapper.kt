package com.kiral.charityapp.domain.util

import com.kiral.charityapp.domain.fake.responses.FakeDonationGoalReponse
import com.kiral.charityapp.domain.model.Project

class ProjectMapper: Mapper<FakeDonationGoalReponse, Project>{
    override fun mapToDomainModel(model: FakeDonationGoalReponse): Project {
        return Project(
            id = model.id,
            charityId =  model.charityId,
            name = model.name,
            description = model.description,
            goalSum = model.goalSum,
            actualSum = model.actualSum,
            charityName = model.name,
            charityImage = model.charityImage,
            charityAdress = model.charityAdress,
            peopleDonated = model.peopleDonated,
            donorDonated =  model.donorDonated
        )
    }

    override fun mapFromDomainModel(domainModel: Project): FakeDonationGoalReponse {
        return FakeDonationGoalReponse(
            id = domainModel.id,
            charityId =  domainModel.charityId,
            name = domainModel.name,
            description = domainModel.description,
            goalSum = domainModel.goalSum,
            actualSum = domainModel.actualSum,
            charityName = domainModel.name,
            charityImage = domainModel.charityImage,
            charityAdress = domainModel.charityAdress,
            peopleDonated = domainModel.peopleDonated,
            donorDonated =  domainModel.donorDonated
        )
    }
}