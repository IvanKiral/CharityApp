package com.kiral.charityapp.domain.util

import com.kiral.charityapp.domain.fake.responses.FakeCharityListResponse
import com.kiral.charityapp.domain.model.CharityListItem

class CharityListItemMapper: Mapper<FakeCharityListResponse, CharityListItem>{
    override fun mapToDomainModel(model: FakeCharityListResponse): CharityListItem {
        return CharityListItem(
            id = model.id,
            imgSrc = model.imgSrc,
            name = model.name
        )
    }

    override fun mapFromDomainModel(domainModel: CharityListItem): FakeCharityListResponse {
        return FakeCharityListResponse(
            id = domainModel.id,
            imgSrc = domainModel.imgSrc,
            name = domainModel.name
        )
    }

    fun mapToDomainModelList(entities: List<FakeCharityListResponse>): List<CharityListItem>{
        return entities.map {e -> mapToDomainModel(e)}
    }

}