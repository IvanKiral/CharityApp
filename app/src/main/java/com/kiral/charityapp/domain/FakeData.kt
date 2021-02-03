package com.kiral.charityapp.domain

import com.kiral.charityapp.R
import com.kiral.charityapp.domain.model.Badge
import com.kiral.charityapp.domain.model.Charity
import com.kiral.charityapp.domain.model.Profile

val badges: List<Badge> = listOf(
    Badge(
        id = 0,
        title = "doggo",
        description = "doggo",
        iconId = R.drawable.ic_dog
    ),
    Badge(
        id = 1,
        title = "doggo",
        description = "doggo",
        iconId = R.drawable.ic_dog
    ),
    Badge(
        id = 2,
        title = "doggo",
        description = "doggo",
        iconId = R.drawable.ic_dog
    ),
    Badge(
        id = 3,
        title = "doggo",
        description = "doggo",
        iconId = R.drawable.ic_dog
    ),
    Badge(
        id = 4,
        title = "doggo",
        description = "doggo",
        iconId = R.drawable.ic_dog
    ),
)

val profile = Profile(
    id = 0,
    email = "email@email.com",
    name = "Rachel Green",
    donations = 87,
    credit = 27.5f,
    automaticDonations = true,
    automaticDonationsValue = 1,
    automaticDonationTimeFrequency = "day",
    badges = badges
)

val charities = listOf(
    Charity(
        imgSrc = "https://images.unsplash.com/photo-1611464765133-794150e205dd?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixlib=rb-1.2.1&q=80&w=800",
        id = 0,
        name = "Domov Mladeze Lovosicka",
        address = "Adressa ",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Facilisi morbi tempus iaculis urna id. Ultrices sagittis orci a scelerisque purus semper eget duis. Justo nec ultrices dui sapien eget mi. Elit duis tristique sollicitudin nibh. Adipiscing elit ut aliquam purus sit amet luctus. Ut sem nulla pharetra diam sit. Ligula ullamcorper malesuada proin libero nunc consequat. Pellentesque habitant morbi tristique senectus et netus. Facilisis gravida neque convallis a cras. Enim nulla aliquet porttitor lacus. Non enim praesent elementum facilisis leo vel fringilla.\n" +
                "\n" +
                "Venenatis a condimentum vitae sapien pellentesque. Massa sed elementum tempus egestas. Molestie at elementum eu facilisis. Eu non diam phasellus vestibulum lorem sed risus ultricies tristique. Ultrices",
        raised = 1250.0f,
        peopleDonated = 187
    ),
    Charity(
        imgSrc = "https://source.unsplash.com/random/800x600",
        id = 1,
        name = "Domov Mladeze Krasna horka",
        address = "Adressa ",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Facilisi morbi tempus iaculis urna id. Ultrices sagittis orci a scelerisque purus semper eget duis. Justo nec ultrices dui sapien eget mi. Elit duis tristique sollicitudin nibh. Adipiscing elit ut aliquam purus sit amet luctus. Ut sem nulla pharetra diam sit. Ligula ullamcorper malesuada proin libero nunc consequat. Pellentesque habitant morbi tristique senectus et netus. Facilisis gravida neque convallis a cras. Enim nulla aliquet porttitor lacus. Non enim praesent elementum facilisis leo vel fringilla.\n" +
                "\n" +
                "Venenatis a condimentum vitae sapien pellentesque. Massa sed elementum tempus egestas. Molestie at elementum eu facilisis. Eu non diam phasellus vestibulum lorem sed risus ultricies tristique. Ultrices",
        raised = 1250.0f,
        peopleDonated = 187
    ),
    Charity(
        imgSrc = "https://source.unsplash.com/random/800x600",
        id = 2,
        name = "Zober loptu nie drogu",
        address = "Adressa ",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Facilisi morbi tempus iaculis urna id. Ultrices sagittis orci a scelerisque purus semper eget duis. Justo nec ultrices dui sapien eget mi. Elit duis tristique sollicitudin nibh. Adipiscing elit ut aliquam purus sit amet luctus. Ut sem nulla pharetra diam sit. Ligula ullamcorper malesuada proin libero nunc consequat. Pellentesque habitant morbi tristique senectus et netus. Facilisis gravida neque convallis a cras. Enim nulla aliquet porttitor lacus. Non enim praesent elementum facilisis leo vel fringilla.\n" +
                "\n" +
                "Venenatis a condimentum vitae sapien pellentesque. Massa sed elementum tempus egestas. Molestie at elementum eu facilisis. Eu non diam phasellus vestibulum lorem sed risus ultricies tristique. Ultrices",
        raised = 1250.0f,
        peopleDonated = 187
    ),
)