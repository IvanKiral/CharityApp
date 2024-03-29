package com.kiral.charityapp.repositories.charities

import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.domain.model.CharityListItem
import com.kiral.charityapp.domain.model.Donor
import com.kiral.charityapp.domain.model.LeaderBoard
import com.kiral.charityapp.domain.model.Project
import com.kiral.charityapp.network.DataState
import kotlinx.coroutines.flow.Flow

interface CharityRepository {
    fun search(id: Int, page:Int, categories: List<Int>): Flow<DataState<List<CharityListItem>>>

    fun get(id: Int, donorId: Int): Flow<DataState<Charity>>

    fun getProject(id: Int, donorId: Int): Flow<DataState<Project>>

    fun makeDonationToCharity(charityId: Int, donorId: Int, projectId:Int?, value: Double): Flow<DataState<Boolean>>

    fun getCharityDonors(charityId: Int, userId: Int?, page: Int, projectId: Int): Flow<DataState<List<Donor>>>

    fun getLeaderboard(userId: Int): Flow<DataState<LeaderBoard>>

    fun addBadge(userId: Int, badgeId: Int): Flow<DataState<Int>>
}