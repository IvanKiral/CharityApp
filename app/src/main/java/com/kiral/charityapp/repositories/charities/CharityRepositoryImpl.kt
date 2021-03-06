package com.kiral.charityapp.repositories.charities

import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.domain.model.CharityListItem
import com.kiral.charityapp.domain.model.Donor
import com.kiral.charityapp.domain.model.Project
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.network.Dto.CharityListItemMapper
import com.kiral.charityapp.network.Dto.CharityMapper
import com.kiral.charityapp.network.Dto.DonationDto
import com.kiral.charityapp.network.Dto.DonorsMapper
import com.kiral.charityapp.network.Dto.ProjectMapper
import com.kiral.charityapp.network.NetworkService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class CharityRepositoryImpl(
    private val charityMapper: CharityMapper,
    private val charityGoalMapper: ProjectMapper,
    private val charityListMapper: CharityListItemMapper,
    private val donorsMapper: DonorsMapper,
    private val networkService: NetworkService
) : CharityRepository {
    override fun search(id: Int, categories: List<Int>): Flow<DataState<List<CharityListItem>>> =
        flow {
            try {
                emit(DataState.Loading)
                //only for showing loading state
                kotlinx.coroutines.delay(2000)
                val response = networkService.getCharities(1, id, categories)
                if(response.isSuccessful){
                    emit(DataState.Success(charityListMapper.mapToDomainModelList(response.body()!!.charities)))
                } else {
                    emit(DataState.Error("An error has occured! Please retry later."))
                }
            } catch (e: IOException) {
                emit(DataState.Error("An error has occured! Please retry later."))
            }
        }

    override fun get(id: Int, donorId: Int): Flow<DataState<Charity>> = flow {
        try {
            emit(DataState.Loading)
            val response = networkService.getCharity(id, donorId)
            if(response.isSuccessful) {
                emit(DataState.Success(charityMapper.mapToDomainModel(response.body()!!)))
            } else {
                emit(DataState.Error("An error has occured! Please retry later."))
            }
        } catch (e: IOException) {
            emit(DataState.Error("An error has occured! Please retry later."))
        }
    }

    override fun getProject(id: Int, donorId: Int): Flow<DataState<Project>> = flow {
        try {
            emit(DataState.Loading)
            val response = networkService.getCharityGoal(id, donorId)
            if(response.isSuccessful) {
                emit(DataState.Success(charityGoalMapper.mapToDomainModel(response.body()!!)))
            } else {
                emit(DataState.Error("An error has occured! Please retry later."))
            }
        } catch (e: Throwable) {
            emit(DataState.Error("An error has occured! Please retry later."))
        }
    }

    override fun makeDonationToCharity(
        charityId: Int,
        donorId: Int,
        projectId: Int?,
        value: Double
    ): Flow<DataState<Boolean>> = flow {
        try {
            emit(DataState.Loading)
            val result = networkService.donate(
                DonationDto(
                    donorId = donorId,
                    charityId = charityId,
                    charityGoalId = projectId,
                    sum = value
                )
            )
            if(result.isSuccessful){
                emit(DataState.Success(true))
            }
            else{
             emit(DataState.Error("Ooops! Something went wrong! Please try your payment later"))
            }
        } catch (e: Throwable) {
            emit(DataState.Error("Ooops! Something went wrong! Please try your payment later"))
        }
    }

    override fun getCharityDonors(charityId: Int, page: Int): Flow<DataState<List<Donor>>> =  flow {
        try {
            emit(DataState.Loading)
            val response = networkService.getCharityDonors(charityId, page)
            if(response.isSuccessful){
                emit(DataState.Success(donorsMapper.mapToDomainModelList(response.body()!!.donors)))
            } else {
                emit(DataState.Error("An error has occured! Please retry later."))
            }
        } catch (e: Throwable) {
            emit(DataState.Error("An error has occured! Please retry later."))
        }
    }
}