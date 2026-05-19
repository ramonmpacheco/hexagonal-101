package com.hexagonal.letterapp.adapter.outbound.viacep

import com.hexagonal.letterapp.domain.model.Address
import com.hexagonal.letterapp.domain.port.outbound.AddressLookupPort
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class ViaCepAddressAdapter(
    restClientBuilder: RestClient.Builder
) : AddressLookupPort {

    private val restClient = restClientBuilder.build()

    override fun findByCep(cep: String, numero: String): Address {
        val cepLimpo = cep.replace("-", "").trim()
        val response = restClient.get()
            .uri("https://viacep.com.br/ws/$cepLimpo/json/")
            .retrieve()
            .body(ViaCepResponse::class.java)
            ?: error("CEP $cep não encontrado")

        check(!response.erro) { "CEP $cep não encontrado" }

        return Address(
            logradouro = response.logradouro,
            numero = numero,
            bairro = response.bairro,
            cidade = response.localidade,
            estado = response.uf
        )
    }
}

data class ViaCepResponse(
    val logradouro: String = "",
    val bairro: String = "",
    val localidade: String = "",
    val uf: String = "",
    val erro: Boolean = false
)
