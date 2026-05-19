# Fluxo Completo

## O pedido de carta, passo a passo

```mermaid
sequenceDiagram
    actor User as Usuário
    participant CTRL as LetterController<br/>(Adapter Inbound)
    participant SRV as SendLetterService<br/>(Domínio)
    participant VIACEP as ViaCepAddressAdapter<br/>(Adapter Outbound)
    participant PERSIST as LetterPersistenceAdapter<br/>(Adapter Outbound)

    User->>CTRL: POST /letters { message, cep, numero }
    CTRL->>SRV: sendLetter.send(message, cep, numero)

    note over SRV: Valida: mensagem ≤ 150 chars

    SRV->>VIACEP: addressLookup.findByCep(cep, numero)
    VIACEP-->>SRV: Address(rua, cidade, estado...)

    SRV->>PERSIST: repository.save(letter)
    PERSIST-->>SRV: Letter (com id gerado)

    SRV-->>CTRL: Letter
    CTRL-->>User: 201 Created { letter }
```

---

## O que o domínio sabe nesse fluxo?

| Etapa | O domínio sabe? |
|---|---|
| Que a entrada veio de HTTP | ❌ Não — poderia ser CLI |
| Que o endereço veio do ViaCEP | ❌ Não — só chama `AddressLookupPort` |
| Que o banco é H2 | ❌ Não — só chama `LetterRepository` |
| Que a mensagem não pode ter mais de 150 chars | ✅ Sim — é regra de negócio |
| Que precisa de um endereço para criar a carta | ✅ Sim — é regra de negócio |

---

## A fronteira do domínio

```mermaid
graph LR
    subgraph EXTERNAL_IN["Mundo externo (entrada)"]
        HTTP["POST /letters"]
        CLI["Linha de Comando"]
    end

    subgraph DOMAIN["Domínio — a fronteira que não vaza"]
        SRV["SendLetterService\n✅ valida mensagem\n✅ monta a carta"]
    end

    subgraph EXTERNAL_OUT["Mundo externo (saída)"]
        VIACEP["ViaCEP API"]
        DB["Banco H2"]
    end

    HTTP -->|adapter inbound| SRV
    CLI -->|adapter inbound| SRV
    SRV -->|porta de saída| VIACEP
    SRV -->|porta de saída| DB
```

O domínio recebe qualquer entrada e delega qualquer saída —
mas a lógica de negócio fica sempre dentro da fronteira.
