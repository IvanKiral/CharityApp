package com.kiral.charityapp.repositories.charities

import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.domain.model.CharityListItem
import com.kiral.charityapp.domain.model.Donor
import com.kiral.charityapp.domain.model.Project
import com.kiral.charityapp.network.Dto.CharityGoalMapper
import com.kiral.charityapp.network.Dto.CharityListItemMapper
import com.kiral.charityapp.network.Dto.CharityMapper
import com.kiral.charityapp.network.Dto.DonationDto
import com.kiral.charityapp.network.Dto.DonorsMapper
import com.kiral.charityapp.network.NetworkService

class CharityRepositoryImpl(
    private val charityMapper: CharityMapper,
    private val charityGoalMapper: CharityGoalMapper,
    private val charityListMapper: CharityListItemMapper,
    private val donorsMapper: DonorsMapper,
    private val networkService: NetworkService
): CharityRepository {
    override suspend fun search(id: Int, region: String): List<CharityListItem> {
        try {
            val charitiesResponse = networkService.getCharities(1, id)
            return charityListMapper.mapToDomainModelList(charitiesResponse.charities)
        } catch (e: Throwable){
            throw e
        }
    }

    override suspend fun get(id: Int, donorId: Int): Charity {
        try{
            val charity = networkService.getCharity(id, donorId)
            return charityMapper.mapToDomainModel(charity)

        } catch (e: Throwable){
            throw e
        }
    }

    override suspend fun getProject(id: Int, donorId: Int): Project {
        try{
            val charityGoal = networkService.getCharityGoal(id,donorId)
            return charityGoalMapper.mapToDomainModel(charityGoal)
        } catch (e: Throwable){
            throw e
        }
    }

    override suspend fun makeDonationToCharity(
        charityId: Int,
        donorId: Int,
        projectId: Int?,
        value: Double
    ): Boolean {
        try {
            val result = networkService.donate(DonationDto(
                donorId = donorId,
                charityId = charityId,
                charityGoalId = projectId,
                sum = value
            ))
            return result.code() == 200
        } catch (e: Throwable){
            return false
        }
    }

    override suspend fun getCharityDonors(charityId: Int, page: Int): List<Donor> {
        try {
            val donorsResponse = networkService.getCharityDonors(charityId, page)
            return donorsMapper.mapToDomainModelList(donorsResponse.donors)
        } catch (e: Throwable){
            throw e
        }
    }
}