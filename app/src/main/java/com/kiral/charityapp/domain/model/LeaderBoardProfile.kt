package com.kiral.charityapp.domain.model

data class LeaderBoardProfile(
    val id: Int,
    val order: Int,
    val donated: Double,
    val name: String,
    val email: String

)