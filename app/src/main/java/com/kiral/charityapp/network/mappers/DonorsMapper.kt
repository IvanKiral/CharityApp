package com.kiral.charityapp.network.mappers

import com.kiral.charityapp.domain.model.Badge
import com.kiral.charityapp.domain.model.Donor
import com.kiral.charityapp.network.dtos.DonorDto
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

    fun mapToDomainModelList(models: List<DonorDto>): List<Donor>{
        return models.map{
            mapToDomainModel(it)
        }
    }
}