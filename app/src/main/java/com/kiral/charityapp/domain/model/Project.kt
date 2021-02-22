package com.kiral.charityapp.domain.model

data class Project(
    val id: Int,
    val charityId: Int,
    val donorId: Int,
    val name: String,
    val description: String,
    val goalSum: Double,
    var actualSum: Double,
    val charityName: String,
    val charityAdress: String,
    val charityImage: String,
    var peopleDonated: Int,
    var donorDonated: Double
)

//TODO: delete if not usefull
/*val proj = Project(
    id = 0,
    charityId = 0,
    name= "Test",
    description = "fdsafdsafdasfsa",
    goalSum = 15000.0,
    actualSum = 1000.566,
    charityName = "dfsafdsaf",
    charityAdress = "dfasfdasdf",
    charityImage = "https://images.unsplash.com/photo-1457914109735-ce8aba3b7a79?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80",
    peopleDonated = 150
)*/