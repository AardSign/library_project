# Library Project – Sistema de Gerenciamento de Biblioteca

Projeto desenvolvido com o objetivo de aplicar conceitos de **desenvolvimento back-end em Java**, criação de **API REST** e integração com um **front-end em React**.

O sistema permite o gerenciamento de uma biblioteca, possibilitando o cadastro, consulta e organização de livros, seguindo boas práticas de arquitetura e separação de responsabilidades.

##  Tecnologias Utilizadas

### Back-end
- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- API REST
- Git

### Front-end
- React
- JavaScript
- Git

##  Arquitetura
O back-end segue o padrão de arquitetura em camadas:
- **Controller**: exposição dos endpoints REST
- **Service**: regras de negócio
- **Repository**: acesso aos dados
- Separação clara entre front-end e back-end
- Consumo da API REST pelo front-end em React

##  Funcionalidades
- CRUD de livros
- CRUD de frequentadores da biblioteca
- Aluguel e devolução de livros
- Controle de usuários com diferentes perfis (Administrador e Funcionário)
- Criação e gerenciamento de usuários
- Integração completa entre front-end e back-end
- Aplicação de regras de negócio no fluxo de aluguel de livros

