package com.kiral.charityapp.network.Dto

import com.kiral.charityapp.domain.model.CharityListItem
import com.kiral.charityapp.domain.util.Mapper

class CharityListItemMapper: Mapper<CharityListItemDto, CharityListItem> {
    override fun mapToDomainModel(model: CharityListItemDto): CharityListItem {
        return CharityListItem(
            id = model.id,
            name = model.name,
            imgSrc = model.imgSrc
        )
    }

    override fun mapFromDomainModel(domainModel: CharityListItem): CharityListItemDto {
        return CharityListItemDto(
            id = domainModel.id,
            name = domainModel.name,
            imgSrc = domainModel.imgSrc
        )
    }

    fun mapToDomainModelList(models: List<CharityListItemDto>): List<CharityListItem>{
        return models.map{ mapToDomainModel(it) }
    }

}