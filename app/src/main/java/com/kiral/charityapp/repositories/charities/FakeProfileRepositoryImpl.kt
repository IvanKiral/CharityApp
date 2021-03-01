package com.kiral.charityapp.repositories.charities

/*class FakeProfileRepositoryImpl(
    private val profileMapper: ProfileMapper,
    private val fakeDatabase: FakeDatabase
): ProfileRepository {
    override suspend fun login(email: String): Int {
        val x = fakeDatabase.login(email)
        return x?.id ?: 0
    }

    override suspend fun register(profile: Profile): Boolean {
        val tmp = profileMapper.mapFromDomainModel(profile)
        return fakeDatabase.registerUser(tmp)
        /*val profileId = fakeProfiles.last().id + 1
        fakeProfiles.add(
            FakeProfile(
                id = profileId,
                name = profile.name,
                region = "svk",
                credit = 0.0,
                email = profile.email
            )
        )
        fakeDonationRepeats.add(
            FakeDonationRepeat(
                id = fakeDonationRepeats.last().id + 1,
                donorId = profileId,
                active = profile.automaticDonations,
                sum = profile.automaticDonationsValue,
                repeatingStatus = profile.automaticDonationTimeFrequency
            )
        )
        return true*/
    }

    override suspend fun getProfile(id: Int): Profile {
        val profile = fakeDatabase.getProfile(id)
        return profileMapper.mapToDomainModel(profile)
        /*val x = fakeProfiles.filter { p -> p.email == email }.first()
        val donationRepeat = fakeDonationRepeats.filter { d -> d.donorId == x.id }.first()
        return  if(x == null) x
        else Profile(
            id = x.id,
            email = x.email,
            name = x.name,
            donations = fakeDonations.filter{ d -> d.donorId == x.id }.size,
            credit = x.credit,
            charities = "",
            automaticDonationsValue = donationRepeat.sum,
            automaticDonationTimeFrequency = donationRepeat.repeatingStatus,
            automaticDonations = donationRepeat.active,
            badges = badges
        )*/
    }

    override suspend fun getBadges(donorId: Int): List<Badge> {
        val lst = mutableListOf<Badge>()
        badgesMap.forEach{(key, value) ->
            lst.add(
                Badge(
                    id = 0,
                    title = value.title,
                    active = Random.nextBoolean(),
                    iconId = value.icon
                )
            )
        }
        return lst.sortedBy { b -> b.active }
    }
}*/