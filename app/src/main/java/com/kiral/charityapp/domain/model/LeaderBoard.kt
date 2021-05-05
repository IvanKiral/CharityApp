package com.kiral.charityapp.domain.model

data class LeaderBoard(
    val rank: Int,
    val donors: List<LeaderBoardProfile>
)