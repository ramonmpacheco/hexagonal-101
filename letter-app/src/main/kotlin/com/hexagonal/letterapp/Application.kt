package com.hexagonal.letterapp

import com.hexagonal.letterapp.adapter.inbound.cli.CliProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(CliProperties::class)
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
