package com.kiral.charityapp.network.Dto

import com.kiral.charityapp.domain.model.LeaderBoardProfile
import com.kiral.charityapp.domain.util.Mapper

class LeaderboardMapper: Mapper<LeaderboardDto, LeaderBoardProfile> {
    override fun mapToDomainModel(model: LeaderboardDto): LeaderBoardProfile {
        return LeaderBoardProfile(
            email = model.email,
            name = model.name,
            donated = model.sum
        )
    }

    override fun mapFromDomainModel(domainModel: LeaderBoardProfile): LeaderboardDto {
        return LeaderboardDto(
            email = domainModel.email,
            name = domainModel.name,
            sum = domainModel.donated
        )
    }

    fun mapFromDomainModelList(models: List<LeaderboardDto>): List<LeaderBoardProfile>{
        return models.map{ mapToDomainModel(it) }
    }
}