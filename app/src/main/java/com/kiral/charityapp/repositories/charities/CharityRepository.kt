package com.kiral.charityapp.repositories.charities

import com.kiral.charityapp.domain.model.Charity

interface CharityRepository {
    fun search(email: String, region: String): List<Charity>

    fun get(id: Int, donorEmail:String): Charity
}