package com.kiral.charityapp.repositories.charities

import android.util.Log
import com.kiral.charityapp.domain.model.Badge
import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.network.Dto.LoginDto
import com.kiral.charityapp.network.Dto.ProfileMapper
import com.kiral.charityapp.network.ProfileService
import com.kiral.charityapp.utils.badgesMap
import kotlin.random.Random

class ProfileRepositoryImpl(
    private val profileService: ProfileService,
    private val profileMapper: ProfileMapper
): ProfileRepository {
    override suspend fun login(email: String): Int? {
        try{
            val response = profileService.login(LoginDto(email = email))
            if(response.code() == 200){
                return response.body()!!.id
            } else{
                return null
            }
        } catch (e : Throwable){
            return null
        }
    }

    override suspend fun register(profile: Profile): Boolean {
        try {
            val profileDto = profileMapper.mapFromDomainModel(profile)
            Log.i("beforeRegister", "here")
            val response = profileService.register(profileDto)
            Log.i("afterRegister", "here")
            return response.code() == 201
        } catch (e: Throwable) {
            Log.i("RegisterTest", "error is $e")
            return false
        }
    }

    override suspend fun getProfile(id: Int): Profile {
        try {
            val profile = profileService.getProfile(id)
            return profileMapper.mapToDomainModel(profile)
        } catch (e: Throwable) {
            throw e
        }
    }

    override suspend fun getBadges(donorId: Int): List<Badge> {
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