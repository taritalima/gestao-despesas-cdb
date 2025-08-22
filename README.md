# Despesas CDB

🏦 Descrição  
Sistema de gerenciamento de despesas, com funcionalidades para cadastro e gestão de categorias e despesas, filtragem por período e usuário, atualização e exclusão de registros.

🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Hibernate
- PostgreSQL
- Lombok
- Jakarta Validation API
- Maven
- Git/GitHub

🗂️ Estrutura de Pastas
br.com.cdb.controldespesas
├── controller # Endpoints REST (despesas, categorias)
├── dto # Objetos de entrada e saída da API
├── entity # Entidades JPA mapeadas
├── exception # Classes para tratamento de erros e exceções personalizadas
├── repository # Interfaces para persistência
├── service # Lógica de negócio
└── ControleDespesasApplication.java

📌 Regras de Negócio
👤 Cadastro de Categorias

- Nome único e obrigatório

💰 Cadastro de Despesas

- Descrição e valor obrigatórios
- Cada despesa vinculada a uma categoria e usuário
- Filtragem por categoria, período e usuário
- Atualização e exclusão somente pelo usuário dono da despesa

📥 Rotas da API
👥 Categorias
Método | Rota | Ação
--- | --- | ---
POST | /categorias | Criar nova categoria
DELETE | /categorias/{id} | Remover categoria

🧾 Despesas
Método | Rota | Ação
--- | --- | ---
POST | /despesas | Criar nova despesa
GET | /despesas?usuarioId={id}&categoriaId={id}&de={yyyy-MM-dd}&ate={yyyy-MM-dd} | Listar despesas filtradas por usuário, categoria e período
PUT | /despesas/{id}/{usuarioId} | Atualizar despesa
DELETE | /despesas/{id}/{usuarioId} | Remover despesa

▶️ Como Executar o Projeto
Clone o repositório:

```bash
git clone https://github.com/taritalima/gestao-despesas-cdb.git
cd gestao-despesas-cdb
```
