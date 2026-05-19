package com.hexagonal.letterapp.domain.port.outbound

import com.hexagonal.letterapp.domain.model.Address

interface AddressLookupPort {
    fun findByCep(cep: String, numero: String): Address
}
