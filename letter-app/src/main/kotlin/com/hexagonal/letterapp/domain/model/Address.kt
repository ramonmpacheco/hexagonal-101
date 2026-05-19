package com.hexagonal.letterapp.domain.model

data class Address(
    val logradouro: String,
    val numero: String,
    val bairro: String,
    val cidade: String,
    val estado: String
)
