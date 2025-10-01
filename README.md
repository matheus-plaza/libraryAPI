# Library API 📚

![Status](https://img.shields.io/badge/Status-Concluído-brightgreen)
![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-brightgreen)

API RESTful para um sistema de gerenciamento de biblioteca, desenvolvida como parte de um estudo aprofundado sobre o ecossistema Spring. O projeto aborda desde a arquitetura e acesso a dados até tópicos avançados de segurança, documentação e observabilidade.

## ✅ Projeto Concluído

O projeto foi finalizado, cobrindo todos os tópicos planejados da trilha de aprendizado. As funcionalidades implementadas incluem:

-   Arquitetura base com Spring Boot
-   Acesso a dados com Spring Data JPA
-   Desenvolvimento de endpoints RESTful para as principais entidades (Livros, Autores, etc.)
-   Validação de dados com Bean Validation
-   Implementação de segurança com Spring Security
-   Autenticação via Social Login
-   Criação de um Authorization Server com OAuth2 e JWT
-   Documentação da API com OpenAPI (Swagger)
-   Implementação de Logging, Métricas e Observabilidade com Spring Boot Actuator
-   Containerização com Docker e deploy na nuvem AWS (EC2 e RDS).

## Tecnologias e Conceitos Abordados

Esta API foi construída com as seguintes tecnologias e conceitos:

-   **Linguagem:** Java 21
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
-   `/login` - Login de usuarios

## Pré-requisitos para executar:

-   JDK 17 ou superior
-   Maven 3.8+
-   Uma instância de banco de dados (configurada no `application.yml`) ou usar o H2 em memória.


## Documentação da API (Swagger)

A documentação da API está disponível e pode ser acessada através do endpoint `/swagger-ui/index.html` após a execução da aplicação.

## Autor:

**Matheus Plaza**

-   LinkedIn: matheus-plaza
-   GitHub: matheus-plaza
