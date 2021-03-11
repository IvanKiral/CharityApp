package com.kiral.charityapp.utils

import com.kiral.charityapp.R
import com.kiral.charityapp.domain.model.BadgeData

data class Category(
    val categoryId: Int,
    val nameId: String,
    val active: Boolean
)

object Constants {
    val DONATION_VALUES = listOf(0.5, 1.0, 2.0, 5.0, 10.0, 20.0, 50.0, 100.0, 500.0, 1000.0)

    val GRAVATAR_LINK = "https://www.gravatar.com/avatar/"

    val DONORS_PAGE_SIZE = 5

    val CATEGORIES_NUMBER = 5

    val BADGES = mapOf(
        1 to BadgeData(
            R.string.badge_donated_1,
            R.drawable.ic_badge_donation_one
        ),
        2 to BadgeData(
            R.string.badge_donated_10,
            R.drawable.ic_badge_donation_ten
        ),
        3 to BadgeData(
            R.string.badge_donated_100,
            R.drawable.ic_badge_donation_hundred
        ),
        4 to BadgeData(
            R.string.badge_donated_1000,
            R.drawable.ic_badge_donation_thousand
        ),
        5 to BadgeData(
           R.string.badge_donated_all,
            R.drawable.ic_badge_charity_all
        ),
        6 to BadgeData(
            R.string.badge_environmental,
            R.drawable.ic_badge_charity_enviroment
        ),
        7 to BadgeData(
            R.string.badge_animal,
            R.drawable.ic_badge_charity_animal
        ),
        8 to BadgeData(
            R.string.badge_health,
            R.drawable.ic_badge_charity_art
        ),
        9 to BadgeData(
            R.string.badge_educational,
            R.drawable.ic_badge_charity_scholarship
        ),
        10 to BadgeData(
            R.string.badge_artsAndCulture,
            R.drawable.ic_badge_charity_art
        ),
        11 to BadgeData(
            R.string.badge_streak_month,
            R.drawable.ic_badge_date_month
        ),
    )
}