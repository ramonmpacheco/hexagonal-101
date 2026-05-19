package com.hexagonal.letterapp.adapter.outbound.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface LetterJpaRepository : JpaRepository<LetterEntity, Long>
