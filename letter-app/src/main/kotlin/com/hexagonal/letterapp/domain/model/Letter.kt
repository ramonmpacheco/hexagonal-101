package com.hexagonal.letterapp.domain.model

data class Letter(
    val id: Long? = null,
    val message: String,
    val address: Address
) {
    init {
        validateMessage(message)
    }

    companion object {
        fun validateMessage(message: String) {
            require(message.isNotBlank()) { "Mensagem não pode ser vazia" }
            require(message.length <= 150) { "Mensagem não pode ter mais de 150 caracteres" }
        }
    }
}
