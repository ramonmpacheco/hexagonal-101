package com.hexagonal.letterapp.domain.port.outbound

import com.hexagonal.letterapp.domain.model.Letter

interface LetterRepository {
    fun save(letter: Letter): Letter
}
