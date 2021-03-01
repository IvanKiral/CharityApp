package com.kiral.charityapp.network

import com.kiral.charityapp.network.Dto.LoginDto
import com.kiral.charityapp.network.Dto.ProfileDto
import com.kiral.charityapp.network.Responses.LoginResponse
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
    suspend fun getProfile(@Path("donorId") donorId: Int): ProfileDto
}