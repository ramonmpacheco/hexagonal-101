package com.hexagonal.letterapp.adapter.inbound.http

import com.hexagonal.letterapp.domain.model.Letter
import com.hexagonal.letterapp.domain.port.inbound.SendLetterUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/letters")
class LetterController(
    private val sendLetter: SendLetterUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun send(@RequestBody request: SendLetterRequest): Letter =
        sendLetter.send(request.message, request.cep, request.numero)
}
