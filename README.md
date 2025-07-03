# 📊 Trindade CRM

Sistema de CRM (Customer Relationship Management) desenvolvido para a Trindade Isenções, com foco em controle de clientes, produtos, vendas e gestão de usuários com autenticação JWT e controle de acessos.

---

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.4.7
- Spring Security + JWT
- Spring Data JPA + Hibernate
- MySQL
- Swagger/OpenAPI
- Maven

---

## 📁 Estrutura do Projeto

- **/controller** → Endpoints da API REST
- **/model** → Entidades JPA
- **/repository** → Interfaces de acesso a dados
- **/security** → Filtros JWT, utilitários e controle de login
- **/config** → Configurações globais de segurança e inicialização
- **/resources** → Configurações do Spring Boot (application.properties)

---

## 🔐 Acesso à API

A autenticação é feita via **JWT**. É necessário realizar login para obter o token e usá-lo no header das requisições protegidas.

---


### Exemplo do udo do token:

Authorization: Bearer {seu_token_aqui}

---

### Usuários de Teste

| Nome             | E-mail                                          | Senha  | Role        |
| ---------------- | ----------------------------------------------- | ------ | ----------- |
| Gustavo Medeiros | [gustavo@email.com](mailto:gustavo@email.com)   | 123456 | GESTOR      |
| Rebecca Medeiros | [rebecca@email.com](mailto:rebecca@email.com)   | 123456 | VENDEDOR    |
| Fernando         | [fernando@email.com](mailto:fernando@email.com) | 123456 | COLABORADOR |

---

### 🛠️ Como rodar localmente
   - Clone o Repositório
     git clone https://github.com/GustavoMedeiros/trindade_crm.git

   - Configure o banco MySQL:
     CREATE DATABASE trindade_crm;

   - Edite o arquivo src/main/resources/application.properties:
     spring.datasource.url=jdbc:mysql://localhost:3306/trindade_crm
     spring.datasource.username=root
     spring.datasource.password=
     spring.jpa.hibernate.ddl-auto=update

    jwt.secret=suaChaveUltraSecretaAqui
    jwt.expiration=36000000

   - Compile e rode:
     ./mvnw spring-boot:run

---

### 📘 Documentação da API

Após rodar o projeto, acesse a documentação no Swagger:
http://localhost:8080/swagger-ui/index.html

---

### 📦 Funcionalidades

✅ Cadastro e login de usuários
✅ Controle de clientes
✅ Gerenciamento de produtos
✅ Registro e filtro de vendas
✅ Filtragem por data, cliente, produto e vendedor
✅ Validações com mensagens personalizadas
✅ Segurança baseada em perfis de acesso
✅ Documentação completa via Swagger

---

### 🧑‍💻 Autor

Desenvolvido por Gustavo Medeiros
🔗 linkedin.com/in/gustavomedeiros

---

### 📜 Licença

Este projeto é licenciado sob a licença MIT.

### Exemplo de login:
```http
POST /auth/login
Content-Type: application/json

{
  "email": "email@email.com",
  "senha": "123456"
}
