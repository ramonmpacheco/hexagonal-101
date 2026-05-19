package com.hexagonal.letterapp.domain.model

data class Letter(
    val id: Long? = null,
    val message: String,
    val address: Address
)
