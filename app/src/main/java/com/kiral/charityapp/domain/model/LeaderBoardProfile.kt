package com.kiral.charityapp.domain.model

data class LeaderBoardProfile(
    val id: Int = 0,
    val order: Int = 0,
    val donated: Double,
    val name: String,
    val email: String

)