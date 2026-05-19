# Nosso Projeto: App de Cartas

## O que o sistema faz

> Alguém envia uma mensagem (até 150 caracteres) com um CEP e número.
> O sistema busca o endereço, cria uma carta e a salva.

---

## Mapeando nos conceitos do hexágono

```mermaid
graph TB
    subgraph INBOUND["Adapters Inbound (Primários)"]
        REST["LetterController\nPOST /letters"]
        CLI["SendLetterCliRunner\nargs: message cep numero"]
    end

    subgraph DOMAIN["Domínio"]
        direction TB
        UC["Porta Inbound\nSendLetterUseCase"]
        SRV["SendLetterService\n• valida 150 chars\n• monta Letter"]
        REPO_PORT["Porta Outbound\nLetterRepository"]
        ADDR_PORT["Porta Outbound\nAddressLookupPort"]
        UC --> SRV
        SRV --> REPO_PORT
        SRV --> ADDR_PORT
    end

    subgraph OUTBOUND["Adapters Outbound (Secundários)"]
        PERSIST["LetterPersistenceAdapter\nSpring Data JPA + H2"]
        VIACEP["ViaCepAddressAdapter\nGET viacep.com.br/ws/{cep}"]
    end

    REST -->|chama| UC
    CLI -->|chama| UC
    REPO_PORT -->|implementado por| PERSIST
    ADDR_PORT -->|implementado por| VIACEP
```

---

## As entidades do domínio

```kotlin
data class Address(
    val logradouro: String,
    val numero: String,
    val bairro: String,
    val cidade: String,
    val estado: String
)

data class Letter(
    val id: Long? = null,
    val message: String,
    val address: Address
)
```

Sem anotações de banco. Sem anotações de JSON. Kotlin puro.

---

## As portas do domínio

```kotlin
// Inbound — o que o sistema oferece
interface SendLetterUseCase {
    fun send(message: String, cep: String, numero: String): Letter
}

// Outbound — o que o sistema precisa
interface AddressLookupPort {
    fun findByCep(cep: String, numero: String): Address
}

interface LetterRepository {
    fun save(letter: Letter): Letter
}
```

---

## Regras que vivem só no domínio

| Regra | Onde fica |
|---|---|
| Mensagem ≤ 150 caracteres | `SendLetterService` |
| Carta precisa de endereço completo | `SendLetterService` |
| `Letter` tem `message` e `address` | Entidade `Letter` |

Nenhuma dessas regras está no controller, no adapter ou no banco.
