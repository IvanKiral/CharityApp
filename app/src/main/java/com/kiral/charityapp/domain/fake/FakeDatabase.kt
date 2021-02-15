package com.kiral.charityapp.domain.fake

import com.kiral.charityapp.domain.badges
import com.kiral.charityapp.domain.fake.responses.FakeCharityResponse
import com.kiral.charityapp.domain.fake.responses.FakeProfilePost
import com.kiral.charityapp.domain.fake.responses.FakeProjectList
import com.kiral.charityapp.domain.model.Profile
import com.kiral.charityapp.domain.profiles
import com.kiral.charityapp.utils.DonationValues
import kotlin.random.Random

class FakeDatabase {

    init{
        MakeFakeUsers()
        MakeFakeDonations()
        MakeFakeDonationsForDonationGoals()
    }

    fun getCharities(email: String, region: String): List<FakeCharityResponse> {
        val user = fakeProfiles.filter { p -> p.email == email }.firstOrNull()
        user?.let { u ->
            return fakeCharities
                .filter { c -> c.region == region }
                .map { c ->
                    FakeCharityResponse(
                        id = c.id,
                        name = c.name,
                        imgSrc = c.imgSrc,
                        address = c.address,
                        region = c.region,
                        description = c.description,
                        raised = fakeDonations.filter { d -> d.charityId == c.id }
                            .sumByDouble { s -> s.sum },
                        peopleDonated = fakeDonations.filter { d -> d.donorId == u.id }.size,
                        donorDonated = fakeDonations.filter { d -> d.donorId == u.id }
                            .sumByDouble { s -> s.sum },
                        projects = listOf()
                    )
                }
        }
        return listOf()
    }

    fun getCharity(charityId: Int, email: String): FakeCharityResponse {
        val user = fakeProfiles.filter { p -> p.email == email }.first()
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
                    peopleDonated = fakeDonations.filter { d -> d.charityId == c.id }.size,
                    donorDonated = fakeDonations.filter { d -> d.donorId == user.id }
                        .sumByDouble { s -> s.sum },
                    projects = fakeDonationGoals.filter { d -> d.charityId == charityId }.map{ d -> FakeProjectList(d.id, d.name) }
                )
            }

    }

    fun registerUser(profile: FakeProfilePost): Boolean{
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

    fun getProfile(email: String): FakeProfilePost {
        val x = fakeProfiles.filter { p -> p.email == email }.first()
        val donationRepeat = fakeDonationRepeats.filter { d -> d.donorId == x.id }.first()
        return FakeProfilePost(
            id = x.id,
            email = x.email,
            name = x.name,
            region = x.region,
            donations = fakeDonations.filter{ d -> d.donorId == x.id }.size,
            credit = x.credit,
            charities = "",
            donationRepeat = donationRepeat.active,
            donationRepeatFrequency = donationRepeat.repeatingStatus,
            donationRepeatValue = donationRepeat.sum,
            badges = badges
        )
    }

    fun login(email:String): FakeProfilePost?{
        val x = fakeProfiles.filter { p -> p.email == email }.firstOrNull()
        return if(x == null) x else getProfile(email)
    }


    private fun MakeFakeUsers(){
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
        names.forEachIndexed{ index, n ->
            fakeProfiles.add(
                FakeProfile (
                id = lastUserId++,
                name = n,
                region = "svk",
                credit = Random.nextDouble(5000.0),
                email = email.get(index)
                )
            )

            fakeDonationRepeats.add(
                FakeDonationRepeat (
                    id = lastDonationRepeatId++,
                    donorId = lastUserId,
                    active = true,
                    sum = values.random(),
                    repeatingStatus = listOf("daily", "weekly", "montly").random()
                )
            )
        }
    }

    private fun MakeFakeDonations(number: Int = 50){
        val values = DonationValues
        val donatorsIdList = fakeProfiles.map { p -> p.id }
        val charitiesIdList = fakeCharities.map{ c -> c.id }
        var lastDonationId = fakeDonations.lastOrNull()?.id?.plus(1) ?: 0
        repeat(number){
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

    private fun MakeFakeDonationsForDonationGoals(number: Int = 20){
        val values = DonationValues
        val donatorsIdList = fakeProfiles.map { p -> p.id }
        val donationGoalsIdList = fakeDonationGoals.map{ c -> Pair(c.id, c.charityId) }
        var lastDonationId = fakeDonations.lastOrNull()?.id?.plus(1) ?: 0
        repeat(number){
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