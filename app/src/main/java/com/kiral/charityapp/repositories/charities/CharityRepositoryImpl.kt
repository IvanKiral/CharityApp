package com.kiral.charityapp.repositories.charities

import com.kiral.charityapp.domain.fake.*
import com.kiral.charityapp.domain.fake.responses.FakeDonationPost
import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.domain.model.Project
import com.kiral.charityapp.domain.util.CharitiesMapper
import com.kiral.charityapp.domain.util.ProjectMapper

class CharityRepositoryImpl(
    private val charityMapper: CharitiesMapper,
    private val projectMapper: ProjectMapper,
    private val fakeDatabse: FakeDatabase
) : CharityRepository {

    override fun search(email: String, region: String): List<Charity> {
        //TODO change Charity to CharityList
        val charitiesResponse = fakeDatabse.getCharities(email, region)
        return charityMapper.mapToDomainModelList(charitiesResponse)
    }

    override fun get(id: Int, donorEmail: String): Charity {
        val tmp = fakeDatabse.getCharity(id, donorEmail)
        return charityMapper.mapToDomainModel(tmp)
    }

    override fun getProject(id: Int, donorEmail: String): Project {
        return projectMapper.mapToDomainModel(fakeDatabse.getProject(id, donorEmail))
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


}