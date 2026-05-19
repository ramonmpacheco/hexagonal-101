# Arquitetura Hexagonal
### (Ports & Adapters)

---

## O que vamos aprender?

Imagine que você construiu um sistema incrível.

Ele funciona perfeitamente com MySQL.

Aí o cliente pede para trocar para PostgreSQL.

> **Resultado:** você precisa mexer em dezenas de arquivos.
> O sistema inteiro treme.

---

## Por que isso acontece?

Porque o **código de negócio** está **colado** nos **detalhes técnicos**.

A lógica que define *o que o sistema faz* fica misturada com
*como ele se comunica* com banco, APIs e interfaces.

---

## A Arquitetura Hexagonal resolve isso.

Criada por **Alistair Cockburn** em 2005,
ela separa o coração do sistema de tudo que é detalhe técnico.

O resultado: trocar banco, trocar API ou trocar a interface
sem tocar em **nenhuma regra de negócio**.

---

## Nosso Roteiro

```mermaid
graph LR
    A[O Problema] --> B[A Ideia Central]
    B --> C[Domínio]
    C --> D[Portas]
    D --> E[Adaptadores]
    E --> F[Fluxo Completo]
    F --> G[Benefícios]
    G --> H[Projeto Prático]
```
