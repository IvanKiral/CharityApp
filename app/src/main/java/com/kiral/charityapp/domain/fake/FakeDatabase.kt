package com.kiral.charityapp.domain.fake

import com.kiral.charityapp.domain.badges
import com.kiral.charityapp.domain.fake.responses.FakeCharityListResponse
import com.kiral.charityapp.domain.fake.responses.FakeCharityResponse
import com.kiral.charityapp.domain.fake.responses.FakeDonationGoalReponse
import com.kiral.charityapp.domain.fake.responses.FakeDonationPost
import com.kiral.charityapp.domain.fake.responses.FakeProfilePost
import com.kiral.charityapp.domain.fake.responses.FakeProjectList
import com.kiral.charityapp.utils.DonationValues
import kotlin.random.Random

class FakeDatabase {

    init {
        MakeFakeUsers()
        MakeFakeDonations()
        MakeFakeDonationsForDonationGoals()
    }

    fun getCharities(id: Int, region: String): List<FakeCharityListResponse> {
        //val user = fakeProfiles.filter { p -> p.email == email }.firstOrNull()
        return fakeCharities
            .filter { c -> c.region == region }
            .map { c ->
                FakeCharityListResponse(
                    id = c.id,
                    name = c.name,
                    imgSrc = c.imgSrc,
                )
            }
    }

    fun getCharity(charityId: Int, donorId: Int): FakeCharityResponse {
        //val user = fakeProfiles.filter { p -> p.email == email }.first()
        return fakeCharities
            .filter { c -> c.id == charityId }
            .first()
            .let { c ->
                FakeCharityResponse(
                    id = c.id,
                    name = c.name,
                    imgSrc = c.imgSrc,
                    address = c.address,
                    region = c.region,
                    description = c.description,
                    raised = fakeDonations.filter { d -> d.charityId == c.id }
                        .sumByDouble { s -> s.sum },
                    donorId = donorId,
                    peopleDonated = fakeDonations.filter { d -> d.charityId == c.id }.size,
                    donorDonated = fakeDonations.filter { d -> d.donorId == donorId && d.charityId == charityId }
                        .sumByDouble { s -> s.sum },
                    projects = fakeDonationGoals.filter { d -> d.charityId == charityId }
                        .map { d -> FakeProjectList(d.id, d.name) }
                )
            }

    }

    fun registerUser(profile: FakeProfilePost): Boolean {
        val profileId = fakeProfiles.last().id + 1
        fakeProfiles.add(
            FakeProfile(
                id = profileId,
                name = profile.name,
                region = profile.region,
                credit = 0.0,
                email = profile.email
            )
        )
        fakeDonationRepeats.add(
            FakeDonationRepeat(
                id = fakeDonationRepeats.last().id + 1,
                donorId = profileId,
                active = profile.donationRepeat,
                sum = profile.donationRepeatValue,
                repeatingStatus = profile.donationRepeatFrequency
            )
        )
        return true
    }

    fun getProfile(id: Int): FakeProfilePost {
        val x = fakeProfiles.filter { p -> p.id == id }.first()
        val donationRepeat = fakeDonationRepeats.filter { d -> d.donorId == x.id }.first()
        return FakeProfilePost(
            id = x.id,
            email = x.email,
            name = x.name,
            region = x.region,
            donations = fakeDonations.filter { d -> d.donorId == x.id }.size,
            credit = x.credit,
            charities = "",
            donationRepeat = donationRepeat.active,
            donationRepeatFrequency = donationRepeat.repeatingStatus,
            donationRepeatValue = donationRepeat.sum,
            badges = badges
        )
    }

    fun login(email: String): FakeProfilePost? {
        val x = fakeProfiles.filter { p -> p.email == email }.firstOrNull()
        return if (x == null) x else {
            val donationRepeat = fakeDonationRepeats.filter { d -> d.donorId == x.id }.first()
            FakeProfilePost(
                id = x.id,
                email = x.email,
                name = x.name,
                region = x.region,
                donations = fakeDonations.filter { d -> d.donorId == x.id }.size,
                credit = x.credit,
                charities = "",
                donationRepeat = donationRepeat.active,
                donationRepeatFrequency = donationRepeat.repeatingStatus,
                donationRepeatValue = donationRepeat.sum,
                badges = badges
            )
        }
    }

    fun getProject(id: Int, donorId: Int): FakeDonationGoalReponse {
        //val user = fakeProfiles.filter { p -> p.email == email }.first()
        val donationGoal = fakeDonationGoals.filter { d -> d.id == id }.first()
        val charity = fakeCharities.filter { c -> c.id == donationGoal.charityId }.first()
        return FakeDonationGoalReponse(
            id = donationGoal.id,
            charityId = donationGoal.charityId,
            donorId = donorId,
            name = donationGoal.name,
            goalSum = donationGoal.goalSum,
            actualSum = fakeDonations
                .filter { d -> d.donationGoalId == id }
                .sumByDouble { s -> s.sum },
            description = donationGoal.description,
            charityImage = charity.imgSrc,
            charityName = charity.name,
            charityAdress = charity.address,
            peopleDonated = fakeDonations.filter { d -> d.donationGoalId == id }.size,
            donorDonated = fakeDonations
                .filter { d -> d.donationGoalId == id && d.donorId == donorId }
                .sumByDouble { s -> s.sum },
        )
    }

    fun addDonation(donation: FakeDonationPost): Boolean {
        val user = fakeProfiles.filter { p -> p.id == donation.donorId }.first()
        if (user.credit >= donation.sum) {
            fakeDonations.add(
                FakeDonation(
                    id = fakeDonations.last().id + 1,
                    donorId = donation.donorId,
                    charityId = donation.charityId,
                    donationGoalId = donation.donationGoalId,
                    sum = donation.sum
                )
            )
            fakeProfiles[fakeProfiles.indexOf(user)].credit -= donation.sum
            return true
        } else {
            return false
        }
    }


    private fun MakeFakeUsers() {
        val names = listOf(
            "Ivan Smith", "Alžbeta Pekná", "Vít Horváth", "Daniela Nováková",
            "Michal Leto", "Lenka Šikovná", "Martin Šéfko", "Michaela Pracovitá",
            "Ivan Silný", "Alžbeta Múdra",
        )
        val email = listOf(
            "ivansmith@email.com", "alzbetapekna@email.com", "vithorvath@email.com",
            "danielanovakova@email.com", "michalleto@email.com", "lenkasikovna@email.com",
            "martinsefko@email.com", "michaelapracovita@email.com", "ivansilny@email.com",
            "alzbetamudra@email.com"
        )
        val values = listOf<Double>(0.5, 1.0, 2.0, 5.0, 10.0, 20.0, 50.0, 100.0, 500.0, 1000.0)
        var lastUserId = fakeProfiles.lastOrNull()?.id?.plus(1) ?: 0
        var lastDonationRepeatId = fakeDonationRepeats.lastOrNull()?.id?.plus(1) ?: 0
        names.forEachIndexed { index, n ->
            fakeProfiles.add(
                FakeProfile(
                    id = lastUserId++,
                    name = n,
                    region = "svk",
                    credit = Random.nextDouble(5000.0),
                    email = email.get(index)
                )
            )

            fakeDonationRepeats.add(
                FakeDonationRepeat(
                    id = lastDonationRepeatId++,
                    donorId = lastUserId,
                    active = true,
                    sum = values.random(),
                    repeatingStatus = listOf("daily", "weekly", "montly").random()
                )
            )
        }

        fakeProfiles.add(
            FakeProfile(
                id = lastUserId,
                name = "Ivan Kiráľ",
                region = "svk",
                credit = Random.nextDouble(5000.0),
                email = "ivan.kir78@gmail.com"
            )
        )

        fakeDonationRepeats.add(
            FakeDonationRepeat(
                id = lastDonationRepeatId++,
                donorId = lastUserId++,
                active = true,
                sum = values.random(),
                repeatingStatus = listOf("daily", "weekly", "montly").random()
            )
        )
    }

    private fun MakeFakeDonations(number: Int = 50) {
        val values = DonationValues
        val donatorsIdList = fakeProfiles.map { p -> p.id }
        val charitiesIdList = fakeCharities.map { c -> c.id }
        var lastDonationId = fakeDonations.lastOrNull()?.id?.plus(1) ?: 0
        repeat(number) {
            fakeDonations.add(
                FakeDonation(
                    id = lastDonationId++,
                    donorId = donatorsIdList.random(),
                    charityId = charitiesIdList.random(),
                    donationGoalId = null,
                    sum = values.random()
                )
            )
        }
    }

    private fun MakeFakeDonationsForDonationGoals(number: Int = 20) {
        val values = DonationValues
        val donatorsIdList = fakeProfiles.map { p -> p.id }
        val donationGoalsIdList = fakeDonationGoals.map { c -> Pair(c.id, c.charityId) }
        var lastDonationId = fakeDonations.lastOrNull()?.id?.plus(1) ?: 0
        repeat(number) {
            val p = donationGoalsIdList.random()
            fakeDonations.add(
                FakeDonation(
                    id = lastDonationId++,
                    donorId = donatorsIdList.random(),
                    charityId = p.second,
                    donationGoalId = p.first,
                    sum = values.random()
                )
            )
        }
    }
}