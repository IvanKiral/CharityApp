package com.kiral.charityapp.repositories.charities

import com.kiral.charityapp.domain.model.Charity

interface CharityRepository {
    fun search(): List<Charity>

    fun get(id: Int): Charity
}