package com.kiral.charityapp.repositories.charities

import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.domain.model.CharityListItem
import com.kiral.charityapp.domain.model.Donor
import com.kiral.charityapp.domain.model.Project

interface CharityRepository {
    suspend fun search(id: Int, region: String): List<CharityListItem>

    suspend fun get(id: Int, donorId: Int): Charity

    suspend fun getProject(id: Int, donorId: Int): Project

    suspend fun makeDonationToCharity(charityId: Int, donorId: Int, projectId:Int?, value: Double): Boolean

    suspend fun getCharityDonors(charityId: Int, page: Int): List<Donor>
}