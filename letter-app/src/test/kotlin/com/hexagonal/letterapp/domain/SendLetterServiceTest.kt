package com.hexagonal.letterapp.domain

import com.hexagonal.letterapp.application.service.SendLetterService
import com.hexagonal.letterapp.domain.model.Address
import com.hexagonal.letterapp.domain.model.Letter
import com.hexagonal.letterapp.domain.port.outbound.AddressLookupPort
import com.hexagonal.letterapp.domain.port.outbound.LetterRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

// Nenhum import de Spring, JPA ou HTTP aqui.
// O domínio é testado com mocks das suas próprias interfaces.
class SendLetterServiceTest {

    private val fakeAddress = Address(
        logradouro = "Av. Paulista",
        numero = "1000",
        bairro = "Bela Vista",
        cidade = "São Paulo",
        estado = "SP"
    )

    private val addressLookup: AddressLookupPort = mockk {
        every { findByCep(any(), any()) } returns fakeAddress
    }

    private val letterRepository: LetterRepository = mockk {
        every { save(any()) } answers { firstArg<Letter>().copy(id = 1L) }
    }

    private val service = SendLetterService(letterRepository, addressLookup)

    @Test
    fun `deve enviar carta com sucesso`() {
        val carta = service.send("Olá, tudo bem?", "01310100", "1000")

        assertNotNull(carta.id)
        assertEquals("Olá, tudo bem?", carta.message)
        assertEquals("Av. Paulista", carta.address.logradouro)
        verify(exactly = 1) { addressLookup.findByCep("01310100", "1000") }
        verify(exactly = 1) { letterRepository.save(any()) }
    }

    @Test
    fun `deve rejeitar mensagem com mais de 150 caracteres`() {
        val mensagemLonga = "x".repeat(151)

        assertThrows<IllegalArgumentException> {
            service.send(mensagemLonga, "01310100", "1000")
        }

        // O domínio rejeitou antes de chamar qualquer adapter
        verify(exactly = 0) { addressLookup.findByCep(any(), any()) }
        verify(exactly = 0) { letterRepository.save(any()) }
    }

    @Test
    fun `deve rejeitar mensagem vazia`() {
        assertThrows<IllegalArgumentException> {
            service.send("", "01310100", "1000")
        }
    }
}
