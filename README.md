# Library API 📚

![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)
![Java](https://img.shields.io/badge/Java-21%2B-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)

API RESTful para um sistema de gerenciamento de biblioteca, desenvolvida como parte de um estudo aprofundado sobre o ecossistema Spring. O projeto aborda desde a arquitetura e acesso a dados até tópicos avançados de segurança, documentação e observabilidade.

## Status Atual do Projeto

O projeto está sendo construído seguindo uma trilha de aprendizado. Abaixo estão os tópicos já implementados e os próximos passos:

**✅ Implementado:**
-   Arquitetura base com Spring Boot
-   Acesso a dados com Spring Data JPA
-   Desenvolvimento de endpoints RESTful para as principais entidades (Livros, Autores, etc.)
-   Validação de dados com Bean Validation
-   Implementação de segurança com Spring Security
-   Autenticação via Social Login
-   Criação de um Authorization Server com OAuth2 e JWT

**▶️ Próximos Passos:**
-   [ ] Documentação da API com OpenAPI (Swagger)
-   [ ] Implementação de Logging, Métricas e Observabilidade com Spring Boot Actuator

## Tecnologias e Conceitos Abordados

Esta API foi construída com as seguintes tecnologias e conceitos:

-   **Linguagem:** Java 21+
-   **Framework Principal:** Spring Boot 3
-   **Persistência:** Spring Data JPA com Hibernate
-   **Banco de Dados:** PostgreSQL
-   **API:**
    -   Desenvolvimento de APIs RESTful
    -   DTOs (Data Transfer Objects) e Mappers
    -   Tratamento de exceções centralizado (`@ControllerAdvice`)
    -   Validação de dados (`Bean Validation`)
-   **Segurança:**
    -   Spring Security 6
    -   Autenticação e Autorização baseadas em Roles
    -   Login Social (ex: Google, GitHub)
    -   Servidor de Autorização com **OAuth2**
    -   Autenticação via Tokens **JWT (JSON Web Tokens)**
-   **Documentação:**
    -   OpenAPI 3 (Swagger) para documentação de endpoints
-   **Observabilidade:**
    -   Spring Boot Actuator para health-checks e métricas
    -   Logging centralizado

## Estrutura da API

A API é organizada em torno dos seguintes recursos principais:

-   `/livros` - Gerenciamento de livros
-   `/autores` - Gerenciamento de autores
-   `/usuarios` - Gerenciamento de usuários do sistema
-   `/clientes` - Gerenciamento de clientes da biblioteca
-   ... e outros endpoints relacionados.

## Pré-requisitos para executar:

-   JDK 17 ou superior
-   Maven 3.8+
-   Uma instância de banco de dados (configurada no `application.yml`) ou usar o H2 em memória.


## Documentação da API (Swagger)

Estará acessível após a implementação da funcionalidade de documentação

## Autor:

**Matheus Plaza**

-   LinkedIn: matheus-plaza
-   GitHub: matheus-plaza
