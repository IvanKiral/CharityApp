package com.kiral.charityapp.repositories.charities

/*class FakeCharityRepositoryImpl(
    private val charityMapper: CharitiesMapper,
    private val projectMapper: ProjectMapper,
    private val charityListMapper: CharityListItemMapper,
    private val donorsMapper: DonorsMapper,
    private val fakeDatabse: FakeDatabase,
    private val networkService: NetworkService
) : CharityRepository {

    override suspend fun search(id: Int, region: String): List<CharityListItem> {
        try {
            val charitiesResponse = networkService.getCharities(1, id,)
            return charityListMapper.mapToDomainModelList(charitiesResponse.charities)
        } catch (e: Throwable){
            throw e
        }
    }

    override suspend fun get(id: Int, donorId: Int): Charity {
        val tmp = fakeDatabse.getCharity(id, donorId)
        return charityMapper.mapToDomainModel(tmp)
    }

    override suspend fun getProject(id: Int, donorId: Int): Project {
        return projectMapper.mapToDomainModel(fakeDatabse.getProject(id, donorId))
    }

    override suspend fun makeDonationToCharity(charityId: Int, donorId: Int, projectId: Int?, value: Double): Boolean {
        return fakeDatabse.addDonation(
            FakeDonationPost(
                charityId = charityId,
                donorId = donorId,
                donationGoalId = projectId,
                sum = value,
            )
        )
    }

    override suspend fun getCharityDonors(charityId: Int, page: Int): List<Donor> {
        val x = fakeDatabse.getCharityDonors(charityId, page).donors
        return donorsMapper.mapToDomainModelList(x)
    }
}*/