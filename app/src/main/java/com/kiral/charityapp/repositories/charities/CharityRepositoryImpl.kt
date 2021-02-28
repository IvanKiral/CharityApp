package com.kiral.charityapp.repositories.charities

import com.kiral.charityapp.domain.fake.FakeDatabase
import com.kiral.charityapp.domain.fake.responses.FakeDonationPost
import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.domain.model.CharityListItem
import com.kiral.charityapp.domain.model.Donor
import com.kiral.charityapp.domain.model.Project
import com.kiral.charityapp.domain.util.CharitiesMapper
import com.kiral.charityapp.domain.util.CharityListItemMapper
import com.kiral.charityapp.domain.util.DonorsMapper
import com.kiral.charityapp.domain.util.ProjectMapper

class CharityRepositoryImpl(
    private val charityMapper: CharitiesMapper,
    private val projectMapper: ProjectMapper,
    private val charityListMapper: CharityListItemMapper,
    private val donorsMapper: DonorsMapper,
    private val fakeDatabse: FakeDatabase
) : CharityRepository {

    override fun search(id: Int, region: String): List<CharityListItem> {
        val charitiesResponse = fakeDatabse.getCharities(id, region)
        return charityListMapper.mapToDomainModelList(charitiesResponse)
    }

    override fun get(id: Int, donorId: Int): Charity {
        val tmp = fakeDatabse.getCharity(id, donorId)
        return charityMapper.mapToDomainModel(tmp)
    }

    override fun getProject(id: Int, donorId: Int): Project {
        return projectMapper.mapToDomainModel(fakeDatabse.getProject(id, donorId))
    }

    override fun makeDonationToCharity(charityId: Int, donorId: Int, projectId: Int?, value: Double): Boolean {
        return fakeDatabse.addDonation(
            FakeDonationPost(
                charityId = charityId,
                donorId = donorId,
                donationGoalId = projectId,
                sum = value,
            )
        )
    }

    override fun getCharityDonors(charityId: Int, page: Int): List<Donor> {
        val x = fakeDatabse.getCharityDonors(charityId, page).donors
        return donorsMapper.mapToDomainModelList(x)
    }
}