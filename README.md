# Sistema de Recrutamento Interno

Este é um sistema de recrutamento interno desenvolvido com Spring Boot e Angular.

## Requisitos

- Java 11+
- Node.js 14+
- PostgreSQL 12+
- Maven 3.6+

## Configuração do Banco de Dados

1. Crie um banco de dados PostgreSQL chamado `recrutamento`
2. Configure as credenciais no arquivo `backend/src/main/resources/application.properties`

## Executando o Backend

1. Navegue até a pasta `backend`
2. Execute: `mvn clean install`
3. Execute: `mvn spring-boot:run`
4. O backend estará disponível em `http://localhost:8080`

## Executando o Frontend

1. Navegue até a pasta `frontend`
2. Execute: `npm install`
3. Execute: `ng serve`
4. O frontend estará disponível em `http://localhost:4200`

## Funcionalidades

- Sistema de autenticação com JWT
- Cadastro e gerenciamento de vagas
- Sistema de candidaturas
- Painel do candidato
- Interface responsiva com Angular Material

## Estrutura do Projeto

### Backend
- `/src/main/java/com/pacto/recrutamento`
  - `/config` - Configurações do Spring
  - `/controller` - Controllers REST
  - `/model` - Entidades JPA
  - `/repository` - Repositórios JPA
  - `/service` - Camada de serviço
  - `/security` - Configurações de segurança

### Frontend
- `/src/app`
  - `/components` - Componentes Angular
  - `/services` - Serviços
  - `/models` - Interfaces e tipos
  - `/guards` - Guards de autenticação
