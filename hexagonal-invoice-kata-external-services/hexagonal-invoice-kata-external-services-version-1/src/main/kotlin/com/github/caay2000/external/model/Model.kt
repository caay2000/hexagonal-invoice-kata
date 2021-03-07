package com.github.caay2000.external.model

data class Product(
    val id: String,
    val name: String,
    val price: Int
)

data class Account(
    val id: String,
    val name: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val email: String
)