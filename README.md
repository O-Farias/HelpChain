# HelpChain

## Visão Geral

O **HelpChain** é uma aplicação inovadora para gerenciar doações e campanhas beneficentes. Criado com foco em facilitar a conexão entre doadores e ONGs, o sistema permite a criação, gerenciamento e acompanhamento de campanhas de maneira eficiente.

## Funcionalidades Principais

- **Gerenciamento de Campanhas**: 
  - Criação, listagem e exclusão de campanhas beneficentes.
  - Listagem de campanhas ativas.
  - Listagem de campanhas por ONG responsável.

- **Gerenciamento de Usuários**: 
  - Criação de usuários com roles específicas.
  - Listagem e exclusão de usuários.

- **Gerenciamento de Doações**: 
  - Criação de doações associadas a campanhas e doadores.
  - Listagem de doações por campanha ou por doador.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.1.0**
- **H2 Database** (Banco de dados em memória para desenvolvimento)
- **JUnit 5** (Testes unitários e de integração)
- **Mockito** (Simulação de serviços)
- **Maven** (Gerenciamento de dependências)

## Estrutura do Projeto

A estrutura principal do projeto está organizada da seguinte forma:

```
src/
├── main/
│   ├── java/
│   │   └── com/helpchain/
│   │       ├── controllers/   # Controladores da API
│   │       ├── models/        # Entidades do domínio
│   │       ├── services/      # Lógica de negócios
│   │       └── config/        # Configurações do Spring Security
│   └── resources/
│       ├── application.properties  # Configuração do banco e servidor
├── test/
│   └── java/
│       └── com/helpchain/
│           ├── controllers/   # Testes dos controladores
│           ├── services/      # Testes dos serviços
```

## Endpoints da API

### Usuários
- **POST** `/api/users` - Cria um novo usuário.
- **GET** `/api/users/{id}` - Retorna um usuário pelo ID.
- **GET** `/api/users` - Lista todos os usuários.
- **DELETE** `/api/users/{id}` - Exclui um usuário.

### Campanhas
- **POST** `/api/campaigns` - Cria uma nova campanha.
- **GET** `/api/campaigns` - Lista todas as campanhas.
- **GET** `/api/campaigns/active` - Lista campanhas ativas.
- **GET** `/api/campaigns/owner/{ownerId}` - Lista campanhas de um dono específico.
- **DELETE** `/api/campaigns/{id}` - Exclui uma campanha.

### Doações
- **POST** `/api/donations` - Cria uma nova doação.
- **GET** `/api/donations/campaign/{campaignId}` - Lista doações de uma campanha específica.
- **GET** `/api/donations/donor/{donorId}` - Lista doações feitas por um doador específico.

## Testes

### Testes Implementados

- **Controladores**:
  - `DonationControllerTest`
  - `UserControllerTest`
  - `CampaignControllerTest`

- **Serviços**:
  - `DonationServiceTest`
  - `UserServiceTest`
  - `CampaignServiceTest`

Os testes abrangem cenários principais de sucesso e falhas comuns, garantindo a confiabilidade do sistema.

### Executando os Testes

Para rodar os testes, execute o comando:

```bash
mvn test
```

## Configuração do Ambiente

1. Clone o repositório:
   ```bash
   git clone https://github.com/O-Farias/helpchain.git
   ```
2. Navegue até o diretório do projeto:
   ```bash
   cd helpchain
   ```
3. Compile o projeto:
   ```bash
   mvn clean install
   ```
4. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

A aplicação estará disponível em: [http://localhost:8080](http://localhost:8080)

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir uma issue ou enviar um pull request.

## Licença

Este projeto é licenciado sob a **MIT License**. Consulte o arquivo `LICENSE` para mais informações.


