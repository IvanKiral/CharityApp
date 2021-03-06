package com.kiral.charityapp.repositories.charities

import android.util.Log
import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.network.Dto.LoginDto
import com.kiral.charityapp.network.Dto.ProfileMapper
import com.kiral.charityapp.network.Dto.ProfilePostDto
import com.kiral.charityapp.network.ProfileService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class ProfileRepositoryImpl(
    private val profileService: ProfileService,
    private val profileMapper: ProfileMapper
): ProfileRepository {
    override fun login(email: String): Flow<DataState<Int>> = flow {
        try{
            Log.i("AppDebug", "login begin")
            emit(DataState.Loading)
            Log.i("AppDebug", "login after loading")
            val response = profileService.login(LoginDto(email = email))
            Log.i("AppDebug", "after call")
            if(response.isSuccessful){
                Log.i("AppDebug", "success")
                emit(DataState.Success(response.body()!!.id))
            } else{
                when(response.code()) {
                    404 -> emit(DataState.HttpsErrorCode(404, "User not found"))
                }
            }
        } catch (e : IOException){
            Log.i("AppDebug", "OOPS ERROR ${e.localizedMessage}  ${e.message}")
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
                emit(DataState.Error("Could not register! Please try again later"))
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

    override fun updateRegularDonationActive(
        id: Int,
        regularDonationActive: Boolean
    ): Flow<DataState<Boolean>> = flow {
        try{
            emit(DataState.Loading)
            val response = profileService.updateProfile(
                ProfilePostDto(
                    userId = id,
                    regularDonationActive = regularDonationActive,
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

    override fun updateCategories(id: Int, categories: List<Int>): Flow<DataState<Boolean>> = flow {
        try{
            emit(DataState.Loading)
            val response = profileService.updateProfile(
                ProfilePostDto(
                    userId = id,
                    categories = categories,
                )
            )
            if(response.isSuccessful){
                emit(DataState.Success(true))
            }
            else{
                emit(DataState.Error("An error has occurred! Please retry later"))
            }
        }
        catch (e: IOException){
            emit(DataState.Error("An error has occurred! Please retry later"))
        }
    }

    override fun updateRegion(id: Int, region: String): Flow<DataState<Boolean>> = flow {
        try{
            emit(DataState.Loading)
            val response = profileService.updateProfile(
                ProfilePostDto(
                    userId = id,
                    region = region
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

    override fun addCredit(id: Int, credit: Double): Flow<DataState<Boolean>> = flow {
        try{
            emit(DataState.Loading)
            val response = profileService.updateProfile(
                ProfilePostDto(
                    userId = id,
                    credit = credit,
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
}