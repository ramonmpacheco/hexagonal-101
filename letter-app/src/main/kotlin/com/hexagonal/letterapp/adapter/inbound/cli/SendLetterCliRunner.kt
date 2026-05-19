package com.hexagonal.letterapp.adapter.inbound.cli

import com.hexagonal.letterapp.domain.port.inbound.SendLetterUseCase
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component

// Ativo apenas quando a propriedade cli.enabled=true é passada
// Exemplo: ./gradlew bootRun --args='--cli.enabled=true --cli.message="Olá" --cli.cep=01310100 --cli.numero=1000'
@Component
@ConditionalOnProperty(name = ["cli.enabled"], havingValue = "true")
class SendLetterCliRunner(
    private val sendLetter: SendLetterUseCase,
    private val props: CliProperties
) : CommandLineRunner {

    override fun run(vararg args: String) {
        val letter = sendLetter.send(props.message, props.cep, props.numero)
        println("Carta enviada com sucesso!")
        println("ID: ${letter.id}")
        println("Mensagem: ${letter.message}")
        println("Destinatário: ${letter.address.logradouro}, ${letter.address.numero} - ${letter.address.cidade}/${letter.address.estado}")
    }
}
