package com.kiral.charityapp.repositories.charities

import com.kiral.charityapp.domain.model.Badge
import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.network.Dto.LoginDto
import com.kiral.charityapp.network.Dto.ProfileMapper
import com.kiral.charityapp.network.Dto.ProfilePostDto
import com.kiral.charityapp.network.ProfileService
import com.kiral.charityapp.utils.badgesMap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import kotlin.random.Random

class ProfileRepositoryImpl(
    private val profileService: ProfileService,
    private val profileMapper: ProfileMapper
): ProfileRepository {
    override fun login(email: String): Flow<DataState<Int>> = flow {
        try{
            emit(DataState.Loading)
            val response = profileService.login(LoginDto(email = email))
            if(response.isSuccessful){
                emit(DataState.Success(response.body()!!.id))
            } else{
                emit(DataState.Error("Could not login"))
            }
        } catch (e : IOException){
            emit(DataState.Error("An error has ocurred! Please retry later"))
        }
    }

    override fun register(profile: Profile): Flow<DataState<Boolean>> = flow {
        try {
            emit(DataState.Loading)
            val profileDto = profileMapper.mapFromDomainModel(profile)
            val response = profileService.register(profileDto)
            if(response.isSuccessful){
                emit(DataState.Success(true))
            } else {
                emit(DataState.Error("Could not register"))
            }
        } catch (e: IOException) {
            emit(DataState.Error("An error has ocurred! Please retry later"))
        }
    }

    override fun getProfile(id: Int): Flow<DataState<Profile>> = flow {
        try {
            emit(DataState.Loading)
            val response = profileService.getProfile(id)
            if(response.isSuccessful){
                emit(DataState.Success(profileMapper.mapToDomainModel(response.body()!!)))
            } else {
                emit(DataState.Error("No profile with that id"))
            }
        } catch (e: IOException) {
            emit(DataState.Error("An error has ocurred! Please retry later"))
        }
    }

    override fun updateRegularDonation(
        id: Int,
        regularDonationActive: Boolean,
        regularDonationValue: Double?,
        regularDonationFrequency: Int?
    ): Flow<DataState<Boolean>> = flow {
        try{
            emit(DataState.Loading)
            val response = profileService.updateProfile(
                ProfilePostDto(
                    userId = id,
                    regularDonationActive = regularDonationActive,
                    regularDonationValue = regularDonationValue,
                    regularDonationFrequency = regularDonationFrequency,
                    categories = null
                    )
            )
            if(response.isSuccessful){
                emit(DataState.Success(true))
            }
            else{
                emit(DataState.Error("An error has ocurred! Please retry later"))
            }
        }
        catch (e: IOException){
            emit(DataState.Error("An error has ocurred! Please retry later"))
        }
    }



    override fun getBadges(donorId: Int): List<Badge> {
        val lst = mutableListOf<Badge>()
        badgesMap.forEach{(_, value) ->
            lst.add(
                Badge(
                    id = 0,
                    title = value.title,
                    active = Random.nextBoolean(),
                    iconId = value.icon
                )
            )
        }
        return lst.sortedBy { b -> b.active }
    }
}