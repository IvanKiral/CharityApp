package com.kiral.charityapp.repositories.charities

import com.kiral.charityapp.domain.charities
import com.kiral.charityapp.domain.fake.*
import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.domain.util.CharitiesMapper

class CharityRepositoryImpl(
    private val charityMapper: CharitiesMapper,
    private val fakeDatabse: FakeDatabase
): CharityRepository {

    override fun search(email: String, region: String): List<Charity> {
        //TODO change Charity to CharityList
        val charitiesResponse = fakeDatabse.getCharities(email, region)
        return charityMapper.mapToDomainModelList(charitiesResponse)

        /*return fakeCharities.map { c ->
            Charity(
                id = c.id,
                name = c.name,
                imgSrc = c.imgSrc,
                address =  c.address,
                description = c.description,
                peopleDonated = fakeDonations.filter { d -> d.charityId == c.id }.toHashSet().size,
                raised = fakeDonations.filter { d -> d.charityId == c.id}.sumByDouble { a -> a.sum }.toFloat(),
                donorDonated = fakeDonations.filter { d -> d.charityId == c.id}.sumByDouble { a -> a.sum }

            )
        }*/
        //return charities
    }

    override fun get(id: Int,donorEmail: String): Charity{
        val tmp = fakeDatabse.getCharity(id, donorEmail)
        return charityMapper.mapToDomainModel(tmp)
        //val charity = fakeCharities.get(id)
        //val donor = fakeProfiles.filter { d -> d.email == donorEmail }.first()
        /*return Charity(
            id = charity.id,
            imgSrc =  charity.imgSrc,
            name = charity.name,
            address =  charity.address,
            description = charity.description,
            peopleDonated = fakeDonations.filter { d -> d.charityId == charity.id }.toHashSet().size,
            raised = fakeDonations.filter { d -> d.charityId == charity.id}.sumByDouble { a -> a.sum }.toFloat(),
            donorDonated = fakeDonations.filter { d -> d.donorId == donor.id }.sumByDouble { s -> s.sum }
        )*/
    }
}