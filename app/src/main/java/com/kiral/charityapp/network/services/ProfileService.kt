package com.kiral.charityapp.network.services

import com.kiral.charityapp.network.dtos.LoginDto
import com.kiral.charityapp.network.dtos.ProfileDto
import com.kiral.charityapp.network.dtos.ProfilePostDto
import com.kiral.charityapp.network.responses.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProfileService {

    @POST("login")
    suspend fun login(@Body loginDto: LoginDto): Response<LoginResponse>

    @PUT("register")
    suspend fun register(@Body loginDto: ProfileDto): Response<LoginResponse>

    @GET("donor/{donorId}")
    suspend fun getProfile(@Path("donorId") donorId: Int): Response<ProfileDto>

    @PUT("user")
    suspend fun updateProfile(@Body profileDto: ProfilePostDto): Response<Unit>
}