package com.hexagonal.letterapp.adapter.inbound.cli

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "cli")
data class CliProperties(
    val message: String = "",
    val cep: String = "",
    val numero: String = ""
)
