package com.hexagonal.letterapp.domain.port.inbound

import com.hexagonal.letterapp.domain.model.Letter

interface SendLetterUseCase {
    fun send(message: String, cep: String, numero: String): Letter
}
