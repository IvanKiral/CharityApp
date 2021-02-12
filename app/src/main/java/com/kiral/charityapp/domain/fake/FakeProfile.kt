package com.kiral.charityapp.domain.fake

data class FakeProfile(
    val id: Int,
    val name: String,
    var region: String,
    var credit: Float,
    val email: String,
    //var categories ??
    //var Birthday
)

var fakeProfiles = listOf(
    FakeProfile(
        id = 0,
        name = "Alžbeta Pekná",
        region = "cze",
        credit = 150f,
        email = "alzbeta@email.com"
    ),
    FakeProfile(
        id = 1,
        name = "Ivan Horváth",
        region = "svk",
        credit = 100f,
        email = "ivan@email.com"
    )
)
