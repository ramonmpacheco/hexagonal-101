package com.hexagonal.letterapp.adapter.outbound.persistence

import com.hexagonal.letterapp.domain.model.Letter
import com.hexagonal.letterapp.domain.port.outbound.LetterRepository
import org.springframework.stereotype.Component

@Component
class LetterPersistenceAdapter(
    private val jpaRepository: LetterJpaRepository
) : LetterRepository {

    override fun save(letter: Letter): Letter =
        jpaRepository.save(letter.toEntity()).toDomain()
}
