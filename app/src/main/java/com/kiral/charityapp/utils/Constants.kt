package com.kiral.charityapp.utils

import com.kiral.charityapp.R
import com.kiral.charityapp.domain.model.BadgeData

val DonationValues = listOf<Double>(0.5, 1.0, 2.0, 5.0, 10.0, 20.0, 50.0, 100.0, 500.0, 1000.0)

val gravatarLink = "https://www.gravatar.com/avatar/"

val DONORS_PAGE_SIZE = 5

val global_categories = listOf(
    "Environment charity", "Animal charity",
    "Health charity", "Education charity", "Art and culture charity"
)

val badgesMap = mapOf<Int, BadgeData>(
    1 to BadgeData(
        "You have donated 1",
        R.drawable.ic_badge_donation_one
    ),
    2 to BadgeData(
        "You have donated 10",
        R.drawable.ic_badge_donation_ten
    ),
    3 to BadgeData(
        "You have donated 100",
        R.drawable.ic_badge_donation_hundred
    ),
    4 to BadgeData(
        "You have donated 1000",
        R.drawable.ic_badge_donation_thousand
    ),
    5 to BadgeData(
        "You have donated to all charities",
        R.drawable.ic_badge_charity_all
    ),
    6 to BadgeData(
        "You have donated enviromental charity",
        R.drawable.ic_badge_charity_enviroment
    ),
    7 to BadgeData(
        "You have donated to animal charity",
        R.drawable.ic_badge_charity_animal
    ),
    8 to BadgeData(
        "You have health charity",
        R.drawable.ic_badge_charity_art
    ),
    9 to BadgeData(
        "You have donated educational charity",
        R.drawable.ic_badge_charity_scholarship
    ),
    10 to BadgeData(
        "You have donated arts and culture charity",
        R.drawable.ic_badge_charity_art
    ),
    11 to BadgeData(
        "You have reached one month streak",
        R.drawable.ic_badge_date_month
    ),
)