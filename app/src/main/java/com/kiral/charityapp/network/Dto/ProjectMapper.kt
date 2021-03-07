package com.kiral.charityapp.network.Dto

import com.kiral.charityapp.domain.model.Project
import com.kiral.charityapp.domain.util.Mapper

class ProjectMapper: Mapper<ProjectDto, Project> {
    override fun mapToDomainModel(model: ProjectDto): Project {
        return Project(
            id = model.id,
            charityId =  model.charityId,
            name = model.name,
            description = model.description,
            goalSum = model.goalSum,
            actualSum = model.actualSum,
            charityName = model.name,
            charityImage = model.imgSrc,
            charityAdress = model.address,
            peopleDonated = model.peopleDonated,
            donorDonated =  model.donorDonated
        )
    }

    override fun mapFromDomainModel(domainModel: Project): ProjectDto {
        TODO("Not yet implemented")
    }

}