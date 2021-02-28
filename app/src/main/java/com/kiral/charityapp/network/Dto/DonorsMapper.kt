package com.kiral.charityapp.network.Dto

import com.kiral.charityapp.domain.model.Donor
import com.kiral.charityapp.domain.util.Mapper

class DonorsMapper: Mapper<DonorDto, Donor> {
    override fun mapToDomainModel(model: DonorDto): Donor {
        return Donor(
            name = model.name,
            email = model.email,
            donated = model.sum,
            badges = listOf()
        )
    }

    override fun mapFromDomainModel(domainModel: Donor): DonorDto {
        return DonorDto(
            name = domainModel.name,
            email = domainModel.email,
            sum = domainModel.donated,
        )
    }

    fun mapToDomainModelList(models: List<DonorDto>): List<Donor>{
        return models.map{
            mapToDomainModel(it)
        }
    }
}