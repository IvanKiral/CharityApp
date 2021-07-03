package com.kiral.charityapp.network.services

import com.kiral.charityapp.network.dtos.AddBadgeDto
import com.kiral.charityapp.network.dtos.CharityDto
import com.kiral.charityapp.network.dtos.DonationDto
import com.kiral.charityapp.network.dtos.ProjectDto
import com.kiral.charityapp.network.responses.CharityListResponse
import com.kiral.charityapp.network.responses.DonorsResponse
import com.kiral.charityapp.network.responses.LeaderboardResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CharityService {

    @GET("charities")
    suspend fun getCharities(
        @Query("page") page: Int,
        @Query("userId") userId: Int,
        @Query("categories") categories: List<Int>
    ): Response<CharityListResponse>

    @GET("charities/{charityId}")
    suspend fun getCharity(
        @Path("charityId") charityId: Int,
        @Query("userId") userId: Int
    ): Response<CharityDto?>

    @GET("projects/{projectId}")
    suspend fun getCharityGoal(
        @Path("projectId") charityId: Int,
        @Query("userId") userId: Int
    ): Response<ProjectDto>

    @GET("charities/{charityId}/donors")
    suspend fun getCharityDonors(
        @Path("charityId") charityId: Int,
        @Query("userId") userId: Int?,
        @Query("page") page: Int,
    ): Response<DonorsResponse>

    @GET("projects/{projectId}/donors")
    suspend fun getProjectDonors(
        @Path("projectId") projectId: Int,
        @Query("userId") userId: Int?,
        @Query("page") page: Int,
    ): Response<DonorsResponse>

    @POST("donate")
    suspend fun donate(@Body donation: DonationDto): Response<Unit>

    @GET("leaderboard/{userId}")
    suspend fun getLeaderboard(
        @Path("userId") userId: Int,
    ): Response<LeaderboardResponse>

    @PUT("user/add_badge")
    suspend fun addBadge(
        @Body badgeBody: AddBadgeDto
    ): Response<Int>

}
