package com.kiral.charityapp.repositories.charities

import com.kiral.charityapp.domain.charities
import com.kiral.charityapp.domain.model.Charity

class CharityRepositoryImpl: CharityRepository {
    override fun search(): List<Charity> {
        return charities
    }

    override fun get(id: Int): Charity = charities[id]
}