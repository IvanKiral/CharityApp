package com.kiral.charityapp.network

import com.kiral.charityapp.network.dtos.CharityDto
import com.kiral.charityapp.network.dtos.DonationDto
import com.kiral.charityapp.network.dtos.ProjectDto
import com.kiral.charityapp.network.responses.CharityListResponse
import com.kiral.charityapp.network.responses.DonorsResponse
import com.kiral.charityapp.network.responses.LeaderboardResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {

    @GET("charities")
    suspend fun getCharities(
        @Query("page") page: Int,
        @Query("donorId") donorId: Int,
        @Query("categories") categories: List<Int>
    ): Response<CharityListResponse>

    @GET("charity")
    suspend fun getCharity(
        @Query("charityId") charityId: Int,
        @Query("donorId") donorId: Int
    ): Response<CharityDto?>

    @GET("project")
    suspend fun getCharityGoal(
        @Query("projectId") charityId: Int,
        @Query("donorId") donorId: Int
    ): Response<ProjectDto>

    @GET("charity/{charityId}/donors/{page}")
    suspend fun getCharityDonors(
        @Path("charityId") charityId: Int,
        @Path("page") page: Int
    ): Response<DonorsResponse>

    @PUT("donate")
    suspend fun donate(@Body donation: DonationDto): Response<DonorsResponse>

    @GET("leaderboard/{userId}")
    suspend fun getLeaderboard(
        @Path("userId") userId: Int,
    ): Response<LeaderboardResponse>

}
