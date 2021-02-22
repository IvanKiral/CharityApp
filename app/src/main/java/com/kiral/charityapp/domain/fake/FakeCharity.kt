package com.kiral.charityapp.domain.fake

data class FakeCharity(
    val id: Int,
    val name: String,
    val imgSrc: String,
    val address: String,
    //val businessName: String,
    val region: String,
    //val category
    val description: String,
)

var fakeCharities = listOf(
    FakeCharity(
        id = 0,
        imgSrc = "https://images.unsplash.com/photo-1488521787991-ed7bbaae773c?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=1050&q=80",
        name = "Domov Mladeze Krasna Hôrka",
        address = "Hlavná 42, 049 41 Krásnohôrske podhradie",
        region = "svk",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Facilisi morbi tempus iaculis urna id. Ultrices sagittis orci a scelerisque purus semper eget duis. Justo nec ultrices dui sapien eget mi. Elit duis tristique sollicitudin nibh. Adipiscing elit ut aliquam purus sit amet luctus. Ut sem nulla pharetra diam sit. Ligula ullamcorper malesuada proin libero nunc consequat. Pellentesque habitant morbi tristique senectus et netus. Facilisis gravida neque convallis a cras. Enim nulla aliquet porttitor lacus. Non enim praesent elementum facilisis leo vel fringilla.\n" +
                "\n" +
                "Venenatis a condimentum vitae sapien pellentesque. Massa sed elementum tempus egestas. Molestie at elementum eu facilisis. Eu non diam phasellus vestibulum lorem sed risus ultricies tristique. Ultrices",
    ),
    FakeCharity(
        id = 1,
        imgSrc = "https://images.unsplash.com/photo-1507146426996-ef05306b995a?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80",
        name = "Útulok Ťlapka",
        address = "Parková 22, 98401 Lučenec",
        region = "svk",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Facilisi morbi tempus iaculis urna id. Ultrices sagittis orci a scelerisque purus semper eget duis. Justo nec ultrices dui sapien eget mi. Elit duis tristique sollicitudin nibh. Adipiscing elit ut aliquam purus sit amet luctus. Ut sem nulla pharetra diam sit. Ligula ullamcorper malesuada proin libero nunc consequat. Pellentesque habitant morbi tristique senectus et netus. Facilisis gravida neque convallis a cras. Enim nulla aliquet porttitor lacus. Non enim praesent elementum facilisis leo vel fringilla.\n" +
                "\n" +
                "Venenatis a condimentum vitae sapien pellentesque. Massa sed elementum tempus egestas. Molestie at elementum eu facilisis. Eu non diam phasellus vestibulum lorem sed risus ultricies tristique. Ultrices",
    ),
    FakeCharity(
        id = 2,
        imgSrc = "https://images.unsplash.com/photo-1498837167922-ddd27525d352?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80",
        name = "Potravinový balíček pre deti",
        address = "Vymyslená 225, 2412 Bratislava",
        region = "svk",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Facilisi morbi tempus iaculis urna id. Ultrices sagittis orci a scelerisque purus semper eget duis. Justo nec ultrices dui sapien eget mi. Elit duis tristique sollicitudin nibh. Adipiscing elit ut aliquam purus sit amet luctus. Ut sem nulla pharetra diam sit. Ligula ullamcorper malesuada proin libero nunc consequat. Pellentesque habitant morbi tristique senectus et netus. Facilisis gravida neque convallis a cras. Enim nulla aliquet porttitor lacus. Non enim praesent elementum facilisis leo vel fringilla.\n" +
                "\n" +
                "Venenatis a condimentum vitae sapien pellentesque. Massa sed elementum tempus egestas. Molestie at elementum eu facilisis. Eu non diam phasellus vestibulum lorem sed risus ultricies tristique. Ultrices",
    ),
    FakeCharity(
        id = 3,
        imgSrc = "https://images.unsplash.com/photo-1537655780520-1e392ead81f2?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80",
        name = "Iniciatíva pre deti",
        address = "Parková 22, 98401 Lučenec",
        region = "svk",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Facilisi morbi tempus iaculis urna id. Ultrices sagittis orci a scelerisque purus semper eget duis. Justo nec ultrices dui sapien eget mi. Elit duis tristique sollicitudin nibh. Adipiscing elit ut aliquam purus sit amet luctus. Ut sem nulla pharetra diam sit. Ligula ullamcorper malesuada proin libero nunc consequat. Pellentesque habitant morbi tristique senectus et netus. Facilisis gravida neque convallis a cras. Enim nulla aliquet porttitor lacus. Non enim praesent elementum facilisis leo vel fringilla.\n" +
                "\n" +
                "Venenatis a condimentum vitae sapien pellentesque. Massa sed elementum tempus egestas. Molestie at elementum eu facilisis. Eu non diam phasellus vestibulum lorem sed risus ultricies tristique. Ultrices",
    ),
    FakeCharity(
        id = 4,
        imgSrc = "https://images.unsplash.com/photo-1421789665209-c9b2a435e3dc?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1051&q=80",
        name = "Zachráň planétu",
        address = "Planetárna 100, 040 01 Košice",
        region = "svk",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Facilisi morbi tempus iaculis urna id. Ultrices sagittis orci a scelerisque purus semper eget duis. Justo nec ultrices dui sapien eget mi. Elit duis tristique sollicitudin nibh. Adipiscing elit ut aliquam purus sit amet luctus. Ut sem nulla pharetra diam sit. Ligula ullamcorper malesuada proin libero nunc consequat. Pellentesque habitant morbi tristique senectus et netus. Facilisis gravida neque convallis a cras. Enim nulla aliquet porttitor lacus. Non enim praesent elementum facilisis leo vel fringilla.\n" +
                "\n" +
                "Venenatis a condimentum vitae sapien pellentesque. Massa sed elementum tempus egestas. Molestie at elementum eu facilisis. Eu non diam phasellus vestibulum lorem sed risus ultricies tristique. Ultrices",
    ),
    FakeCharity(
        id = 5,
        imgSrc = "https://images.unsplash.com/photo-1463740839922-2d3b7e426a56?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=1119&q=80",
        name = "Zdravie pre každého",
        address = "Zdravá 10, 031 01 Liptovský Mikuláš",
        region = "svk",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Facilisi morbi tempus iaculis urna id. Ultrices sagittis orci a scelerisque purus semper eget duis. Justo nec ultrices dui sapien eget mi. Elit duis tristique sollicitudin nibh. Adipiscing elit ut aliquam purus sit amet luctus. Ut sem nulla pharetra diam sit. Ligula ullamcorper malesuada proin libero nunc consequat. Pellentesque habitant morbi tristique senectus et netus. Facilisis gravida neque convallis a cras. Enim nulla aliquet porttitor lacus. Non enim praesent elementum facilisis leo vel fringilla.\n" +
                "\n" +
                "Venenatis a condimentum vitae sapien pellentesque. Massa sed elementum tempus egestas. Molestie at elementum eu facilisis. Eu non diam phasellus vestibulum lorem sed risus ultricies tristique. Ultrices",
    ),
    FakeCharity(
        id = 6,
        imgSrc = "https://images.unsplash.com/photo-1524397057410-1e775ed476f3?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80",
        name = "Kultúrne dedičstvo",
        address = "Kultúrna 1050, 979 01 Rimavská Sobota",
        region = "svk",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Facilisi morbi tempus iaculis urna id. Ultrices sagittis orci a scelerisque purus semper eget duis. Justo nec ultrices dui sapien eget mi. Elit duis tristique sollicitudin nibh. Adipiscing elit ut aliquam purus sit amet luctus. Ut sem nulla pharetra diam sit. Ligula ullamcorper malesuada proin libero nunc consequat. Pellentesque habitant morbi tristique senectus et netus. Facilisis gravida neque convallis a cras. Enim nulla aliquet porttitor lacus. Non enim praesent elementum facilisis leo vel fringilla.\n" +
                "\n" +
                "Venenatis a condimentum vitae sapien pellentesque. Massa sed elementum tempus egestas. Molestie at elementum eu facilisis. Eu non diam phasellus vestibulum lorem sed risus ultricies tristique. Ultrices",
    ),
    FakeCharity(
        id = 7,
        imgSrc = "https://images.unsplash.com/photo-1457914109735-ce8aba3b7a79?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80",
        name = "Útulok Labka",
        address = "Útulná 822, 974 01 Banská Bystrica",
        region = "svk",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Facilisi morbi tempus iaculis urna id. Ultrices sagittis orci a scelerisque purus semper eget duis. Justo nec ultrices dui sapien eget mi. Elit duis tristique sollicitudin nibh. Adipiscing elit ut aliquam purus sit amet luctus. Ut sem nulla pharetra diam sit. Ligula ullamcorper malesuada proin libero nunc consequat. Pellentesque habitant morbi tristique senectus et netus. Facilisis gravida neque convallis a cras. Enim nulla aliquet porttitor lacus. Non enim praesent elementum facilisis leo vel fringilla.\n" +
                "\n" +
                "Venenatis a condimentum vitae sapien pellentesque. Massa sed elementum tempus egestas. Molestie at elementum eu facilisis. Eu non diam phasellus vestibulum lorem sed risus ultricies tristique. Ultrices",
    ),

)