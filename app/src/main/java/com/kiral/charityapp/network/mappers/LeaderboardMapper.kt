package com.kiral.charityapp.network.mappers

import com.kiral.charityapp.domain.model.LeaderBoardProfile
import com.kiral.charityapp.network.dtos.LeaderboardDto

class LeaderboardMapper: Mapper<LeaderboardDto, LeaderBoardProfile> {
    override fun mapToDomainModel(model: LeaderboardDto): LeaderBoardProfile {
        return LeaderBoardProfile(
            email = model.email,
            name = model.name,
            donated = model.sum
        )
    }

    fun mapFromDomainModelList(models: List<LeaderboardDto>): List<LeaderBoardProfile>{
        return models.map{ mapToDomainModel(it) }
    }
}