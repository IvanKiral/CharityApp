package com.kiral.charityapp.repositories.charities

import com.kiral.charityapp.domain.charities
import com.kiral.charityapp.domain.fake.fakeCharities
import com.kiral.charityapp.domain.fake.fakeDonations
import com.kiral.charityapp.domain.fake.fakeProfiles
import com.kiral.charityapp.domain.model.Charity

class CharityRepositoryImpl: CharityRepository {
    override fun search(): List<Charity> {
        //TODO change Charity to CharityList
        return fakeCharities.map { c ->
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
        }
        //return charities
    }

    override fun get(id: Int,donorEmail: String): Charity{
        val charity = fakeCharities.get(id)
        val donor = fakeProfiles.filter { d -> d.email == donorEmail }.first()
        return Charity(
            id = charity.id,
            imgSrc =  charity.imgSrc,
            name = charity.name,
            address =  charity.address,
            description = charity.description,
            peopleDonated = fakeDonations.filter { d -> d.charityId == charity.id }.toHashSet().size,
            raised = fakeDonations.filter { d -> d.charityId == charity.id}.sumByDouble { a -> a.sum }.toFloat(),
            donorDonated = fakeDonations.filter { d -> d.donorId == donor.id }.sumByDouble { s -> s.sum }
        )
    }
}