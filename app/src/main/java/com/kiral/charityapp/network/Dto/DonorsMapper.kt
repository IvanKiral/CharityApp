package com.kiral.charityapp.network.Dto

import com.kiral.charityapp.domain.model.Badge
import com.kiral.charityapp.domain.model.Donor
import com.kiral.charityapp.domain.util.Mapper
import com.kiral.charityapp.utils.Constants.BADGES

class DonorsMapper: Mapper<DonorDto, Donor> {
    override fun mapToDomainModel(model: DonorDto): Donor {
        return Donor(
            name = model.name,
            email = model.email,
            donated = model.sum,
            badges = model.badges.mapNotNull { id ->
                BADGES.get(id)?.let{
                    Badge(
                        id = id,
                        title = it.title,
                        iconId =  it.icon,
                        active = true
                    )
                }
            }
        )
    }

    override fun mapFromDomainModel(domainModel: Donor): DonorDto {
        return DonorDto(
            name = domainModel.name,
            email = domainModel.email,
            sum = domainModel.donated,
            badges = domainModel.badges.map { b -> b.id }
        )
    }

    fun mapToDomainModelList(models: List<DonorDto>): List<Donor>{
        return models.map{
            mapToDomainModel(it)
        }
    }
}