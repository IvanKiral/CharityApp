package com.kiral.charityapp.domain.util

import com.kiral.charityapp.domain.fake.responses.FakeDonorDonation
import com.kiral.charityapp.domain.model.Donor

class DonorsMapper : Mapper<FakeDonorDonation, Donor>{
    override fun mapToDomainModel(model: FakeDonorDonation): Donor {
        return Donor(
            name = model.name,
            email = model.email,
            donated = model.donated,
            badges = listOf()
        )
    }

    override fun mapFromDomainModel(domainModel: Donor): FakeDonorDonation {
        return FakeDonorDonation(
            name = domainModel.name,
            email = domainModel.email,
            donated = domainModel.donated,
        )
    }

    fun mapToDomainModelList(models: List<FakeDonorDonation>): List<Donor>{
        return models.map{
            mapToDomainModel(it)
        }
    }
}