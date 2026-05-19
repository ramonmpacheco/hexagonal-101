package com.hexagonal.letterapp.application.service

import com.hexagonal.letterapp.domain.model.Letter
import com.hexagonal.letterapp.domain.port.inbound.SendLetterUseCase
import com.hexagonal.letterapp.domain.port.outbound.AddressLookupPort
import com.hexagonal.letterapp.domain.port.outbound.LetterRepository
import org.springframework.stereotype.Service

@Service
class SendLetterService(
    private val letterRepository: LetterRepository,
    private val addressLookup: AddressLookupPort
) : SendLetterUseCase {

    override fun send(message: String, cep: String, numero: String): Letter {
        require(message.isNotBlank()) { "Mensagem não pode ser vazia" }
        require(message.length <= 150) { "Mensagem não pode ter mais de 150 caracteres" }

        val address = addressLookup.findByCep(cep, numero)
        val letter = Letter(message = message, address = address)
        return letterRepository.save(letter)
    }
}
