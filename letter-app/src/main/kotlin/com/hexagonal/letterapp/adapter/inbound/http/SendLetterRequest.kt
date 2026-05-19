package com.hexagonal.letterapp.adapter.inbound.http

data class SendLetterRequest(
    val message: String,
    val cep: String,
    val numero: String
)
