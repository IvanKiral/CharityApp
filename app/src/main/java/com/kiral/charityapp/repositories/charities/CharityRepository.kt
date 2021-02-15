package com.kiral.charityapp.repositories.charities

import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.domain.model.Project

interface CharityRepository {
    fun search(email: String, region: String): List<Charity>

    fun get(id: Int, donorEmail:String): Charity

    fun getProject(id: Int, donorEmail: String): Project
}