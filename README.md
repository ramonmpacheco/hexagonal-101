# Arquitetura Hexagonal com Kotlin

Material educativo sobre Arquitetura Hexagonal (Ports & Adapters) composto por slides explicativos e um projeto Kotlin prático.

## Estrutura

```
hex-101/
├── slides/        # 11 slides em Markdown com diagramas Mermaid
└── letter-app/    # Projeto Spring Boot demonstrando a arquitetura
```

## O projeto: App de Cartas

Envia uma mensagem (até 150 caracteres) informando um CEP e número. O sistema busca o endereço via [ViaCEP](https://viacep.com.br) e salva a carta no banco de dados.

## Pré-requisitos

- Java 17+

## Rodando os testes

```bash
cd letter-app
./gradlew test
```

## Subindo o servidor

```bash
cd letter-app
./gradlew bootRun
```

O servidor sobe em `http://localhost:8080`.

## Testando via REST

```bash
curl -X POST http://localhost:8080/letters \
  -H "Content-Type: application/json" \
  -d '{"message": "Olá! Essa é minha primeira carta.", "cep": "01310100", "numero": "1000"}'
```

Para inspecionar as cartas salvas, acesse o console do banco H2 em `http://localhost:8080/h2-console`:
- **JDBC URL:** `jdbc:h2:mem:letterdb`
- **User:** `sa`
- **Password:** *(vazio)*

## Testando via CLI

```bash
cd letter-app
./gradlew bootRun --args='--cli.enabled=true --cli.message="Olá via terminal!" --cli.cep=01310100 --cli.numero=1000'
```
