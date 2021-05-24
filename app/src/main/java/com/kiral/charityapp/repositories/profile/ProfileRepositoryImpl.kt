package com.kiral.charityapp.repositories.profile

import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.network.DataState
import com.kiral.charityapp.network.dtos.AddCreditDto
import com.kiral.charityapp.network.dtos.LoginDto
import com.kiral.charityapp.network.dtos.ProfilePostDto
import com.kiral.charityapp.network.mappers.ProfileMapper
import com.kiral.charityapp.network.services.ProfileService
import com.kiral.charityapp.utils.AssetProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import java.io.IOException

class ProfileRepositoryImpl(
    private val profileService: ProfileService,
    private val profileMapper: ProfileMapper,
    private val assetProvider: AssetProvider
) : ProfileRepository {
    override fun login(email: String, retry: Int): Flow<DataState<Int>> = flow {
        try {
            emit(DataState.Loading)
            val response = profileService.login(LoginDto(email = email))
            if (response.isSuccessful) {
                emit(DataState.Success(response.body()!!.id))
            } else {
                when (response.code()) {
                    404 -> emit(
                        DataState.HttpsErrorCode(
                            code = 404,
                            assetProvider.userNotFound()
                        )
                    )
                }
            }
        } catch (e: IOException) {
            if(retry > 0){
                emitAll(login(email, retry - 1))
            }
            else {
                emit(DataState.Error(assetProvider.networkError()))
            }
        }
    }

    override fun register(profile: Profile): Flow<DataState<Int>> = flow {
        try {
            emit(DataState.Loading)
            val profileDto = profileMapper.mapFromDomainModel(profile)
            val response = profileService.register(profileDto)
            if (response.isSuccessful) {
                emit(DataState.Success(response.body()!!.id))
            } else {
                emit(DataState.Error(assetProvider.networkRegisterError()))
            }
        } catch (e: IOException) {
            emit(DataState.Error(assetProvider.networkError()))
        }
    }

    override fun getProfile(id: Int): Flow<DataState<Profile>> = flow {
        try {
            emit(DataState.Loading)
            val response = profileService.getProfile(id)
            if (response.isSuccessful) {
                emit(DataState.Success(profileMapper.mapToDomainModel(response.body()!!)))
            } else {
                emit(DataState.Error(assetProvider.userNotFound()))
            }
        } catch (e: IOException) {
            emit(DataState.Error(assetProvider.networkError()))
        }
    }

    override fun updateRegularDonation(
        id: Int,
        regularDonationActive: Boolean,
        regularDonationValue: Double?,
        regularDonationFrequency: Int?
    ): Flow<DataState<Boolean>> = flow {
        try {
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
            if (response.isSuccessful) {
                emit(DataState.Success(true))
            } else {
                emit(DataState.Error(assetProvider.networkError()))
            }
        } catch (e: IOException) {
            emit(DataState.Error(assetProvider.networkError()))
        }
    }

    override fun updateRegularDonationActive(
        id: Int,
        regularDonationActive: Boolean
    ): Flow<DataState<Boolean>> = flow {
        try {
            emit(DataState.Loading)
            val response = profileService.updateProfile(
                ProfilePostDto(
                    userId = id,
                    regularDonationActive = regularDonationActive,
                )
            )
            if (response.isSuccessful) {
                emit(DataState.Success(true))
            } else {
                emit(DataState.Error(assetProvider.networkError()))
            }
        } catch (e: IOException) {
            emit(DataState.Error(assetProvider.networkError()))
        }
    }

    override fun updateCategories(id: Int, categories: List<Int>): Flow<DataState<Boolean>> = flow {
        try {
            emit(DataState.Loading)
            val response = profileService.updateProfile(
                ProfilePostDto(
                    userId = id,
                    categories = categories,
                )
            )
            if (response.isSuccessful) {
                emit(DataState.Success(true))
            } else {
                emit(DataState.Error(assetProvider.networkError()))
            }
        } catch (e: IOException) {
            emit(DataState.Error(assetProvider.networkError()))
        }
    }

    override fun updateRegion(id: Int, region: String): Flow<DataState<Boolean>> = flow {
        try {
            emit(DataState.Loading)
            val response = profileService.updateProfile(
                ProfilePostDto(
                    userId = id,
                    region = region
                )
            )
            if (response.isSuccessful) {
                emit(DataState.Success(true))
            } else {
                emit(DataState.Error(assetProvider.networkError()))
            }
        } catch (e: IOException) {
            emit(DataState.Error(assetProvider.networkError()))
        }
    }

    override fun addCredit(id: Int, credit: Double): Flow<DataState<Boolean>> = flow {
        try {
            emit(DataState.Loading)
            val response = profileService.addCredit(
                AddCreditDto(
                    userId = id,
                    credit = credit,
                )
            )
            if (response.isSuccessful) {
                emit(DataState.Success(true))
            } else {
                emit(DataState.Error(assetProvider.networkError()))
            }
        } catch (e: IOException) {
            emit(DataState.Error(assetProvider.networkError()))
        }
    }

    override fun getRankUp(userId: Int): Flow<DataState<Boolean>> = flow {
        try {
            emit(DataState.Loading)
            val response = profileService.getRankUp(userId = userId)
            if (response.isSuccessful) {
                emit(DataState.Success(response.body()!!.rankUp))
            } else {
                emit(DataState.Error(assetProvider.networkError()))
            }
        } catch (e: IOException) {
            emit(DataState.Error(assetProvider.networkError()))
        }
    }
}