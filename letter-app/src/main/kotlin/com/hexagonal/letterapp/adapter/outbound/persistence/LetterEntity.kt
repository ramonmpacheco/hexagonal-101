package com.hexagonal.letterapp.adapter.outbound.persistence

import com.hexagonal.letterapp.domain.model.Address
import com.hexagonal.letterapp.domain.model.Letter
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "letters")
class LetterEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, length = 150)
    val message: String,

    @Column(nullable = false)
    val logradouro: String,

    @Column(nullable = false)
    val numero: String,

    @Column(nullable = false)
    val bairro: String,

    @Column(nullable = false)
    val cidade: String,

    @Column(nullable = false)
    val estado: String
) {
    fun toDomain() = Letter(
        id = id,
        message = message,
        address = Address(
            logradouro = logradouro,
            numero = numero,
            bairro = bairro,
            cidade = cidade,
            estado = estado
        )
    )
}

fun Letter.toEntity() = LetterEntity(
    id = id,
    message = message,
    logradouro = address.logradouro,
    numero = address.numero,
    bairro = address.bairro,
    cidade = address.cidade,
    estado = address.estado
)
