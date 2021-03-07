package com.kiral.charityapp.network.mappers

interface Mapper <From, To>{

    fun mapToDomainModel(model: From): To
}