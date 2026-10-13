# Carteira Fintech — Interfaces em Java

Projeto didático da **Semana 11** da disciplina de **Programação Orientada a Objetos** (UniEVANGÉLICA).
Uma mini carteira de pagamentos (Pix, cartão e boleto) que demonstra, na prática, o conteúdo da aula:
**interface como contrato, implementação múltipla e “programar para a interface”** — aplicando boas
práticas de mercado com **Design Patterns, SOLID, Clean Code e Segurança da Informação**.

> **Stack:** Java 17 · Spring Boot 3.3 · Spring MVC · Thymeleaf · Spring Security · Spring Data JPA.
> **Banco de dados:** funciona com **H2** (padrão, sem instalar nada), **PostgreSQL**, **MySQL/MariaDB** ou **qualquer outro SGBD** — basta escolher um perfil ou informar os dados de conexão (ver a seção [Banco de dados](#-banco-de-dados)).

---

## 🎯 O que este projeto ensina

| Conceito da aula | Onde aparece no código |
|---|---|
| **Interface como contrato** | [`MeioDePagamento`](src/main/java/br/edu/unievangelica/carteira/pagamento/MeioDePagamento.java) |
| **implements / polimorfismo** | [`Pix`](src/main/java/br/edu/unievangelica/carteira/pagamento/Pix.java), [`Cartao`](src/main/java/br/edu/unievangelica/carteira/pagamento/Cartao.java), [`Boleto`](src/main/java/br/edu/unievangelica/carteira/pagamento/Boleto.java) |
| **Implementação múltipla** | as classes acima também implementam [`Auditavel`](src/main/java/br/edu/unievangelica/carteira/pagamento/Auditavel.java) |
| **Método default (Java 8)** | `rotulo()` em `MeioDePagamento` |
| **Programar para a interface (DIP)** | [`TransacaoServiceImpl`](src/main/java/br/edu/unievangelica/carteira/service/TransacaoServiceImpl.java) depende de abstrações |
| **Implementar interface do framework** | [`UsuarioDetailsService`](src/main/java/br/edu/unievangelica/carteira/service/UsuarioDetailsService.java) (`UserDetailsService`) |

Os **padrões de projeto** usados são **Strategy** (os meios de pagamento), **Factory**
([`PagamentoFactory`](src/main/java/br/edu/unievangelica/carteira/pagamento/PagamentoFactory.java)) e
**Repository** (Spring Data JPA). Tudo explicado em detalhe no PDF de documentação:
👉 **[docs/](docs/)**.

---

## 🚀 Começo rápido (sem instalar nada)

Requer apenas o **Java 17+**. O banco padrão é o **H2** (em memória), então funciona de imediato:

```bash
./mvnw spring-boot:run
```

Acesse **http://localhost:8080** e entre com:

- **usuário:** `aluno`
- **senha:** `senha123`

> No Windows, use `mvnw.cmd` no lugar de `./mvnw`. Na primeira execução, o wrapper baixa o Maven automaticamente.
> Não tem o Maven nem quer usar o wrapper? Se tiver o Maven instalado, use `mvn` no lugar de `./mvnw`.

---

## 🗄️ Banco de dados

A aplicação **não está presa a um banco**. Você escolhe entre quatro opções trocando **uma linha**
em [`src/main/resources/application.properties`](src/main/resources/application.properties):

```properties
spring.profiles.active=h2       # padrão: banco em memória, sem instalar nada
# spring.profiles.active=postgres
# spring.profiles.active=mysql
# spring.profiles.active=custom  # qualquer outro SGBD
```

Cada opção tem seu arquivo `application-<perfil>.properties` com os dados de conexão (que você pode
editar, ou sobrescrever por variável de ambiente). Também dá para escolher **sem editar nada**, pela
linha de comando ou por variável de ambiente:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=postgres
# ou
DB_PROFILE=postgres ./mvnw spring-boot:run
```

| Perfil | Banco | Precisa instalar? | Driver incluso? |
|---|---|:--:|:--:|
| `h2` | H2 (memória) | Não | ✅ |
| `postgres` | PostgreSQL | Sim (ou Docker) | ✅ |
| `mysql` | MySQL / MariaDB | Sim (ou Docker) | ✅ |
| `custom` | Qualquer outro SGBD | Sim | ➕ adicionar no `pom.xml` |

> Em todas as opções, o **Hibernate cria as tabelas automaticamente** (`ddl-auto=update`).
> Você só precisa criar o **banco/database vazio** — os passos estão abaixo.

### 1) PostgreSQL

**Com Docker (mais fácil):**

```bash
docker compose up -d           # sobe o PostgreSQL já com o banco "carteira" criado
DB_PROFILE=postgres ./mvnw spring-boot:run
```

**Sem Docker (PostgreSQL instalado na máquina):** crie o banco e o usuário uma vez:

```sql
CREATE DATABASE carteira;
CREATE USER carteira WITH PASSWORD 'troque-esta-senha';
GRANT ALL PRIVILEGES ON DATABASE carteira TO carteira;
```

Depois ajuste, se necessário, a conexão em `application-postgres.properties` e rode com `DB_PROFILE=postgres`.

### 2) MySQL ou MariaDB

**Com Docker:**

```bash
docker compose --profile mysql up -d
DB_PROFILE=mysql ./mvnw spring-boot:run
```

**Sem Docker (MySQL/MariaDB instalado):** crie o banco e o usuário uma vez:

```sql
CREATE DATABASE carteira;
CREATE USER 'carteira'@'%' IDENTIFIED BY 'troque-esta-senha';
GRANT ALL PRIVILEGES ON carteira.* TO 'carteira'@'%';
FLUSH PRIVILEGES;
```

Ajuste a conexão em `application-mysql.properties` (para MariaDB, troque a URL para `jdbc:mariadb://...`
e o driver para `org.mariadb.jdbc.Driver`) e rode com `DB_PROFILE=mysql`.

### 3) Qualquer outro SGBD (SQL Server, Oracle, etc.)

1. **Adicione o driver JDBC** do seu banco no [`pom.xml`](pom.xml) (há um exemplo comentado na seção de dependências).
2. **Crie o banco** de dados vazio no seu SGBD (ex.: `CREATE DATABASE carteira;`).
3. **Informe a conexão** em [`application-custom.properties`](src/main/resources/application-custom.properties)
   (ou por variáveis de ambiente `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `DB_DRIVER`):

   ```properties
   spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=carteira;encrypt=false
   spring.datasource.username=carteira
   spring.datasource.password=troque-esta-senha
   spring.datasource.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
   ```

4. Rode com `DB_PROFILE=custom`. O Hibernate detecta o dialeto do banco automaticamente.

> **Segredos:** em vez de deixar a senha no arquivo, copie `.env.example` para `.env` e carregue as
> variáveis no terminal: `export $(grep -v '^#' .env | xargs)` (Linux/macOS). O `.env` está no `.gitignore`.

---

## 🔐 Boas práticas de Segurança aplicadas

- **Senhas com hash BCrypt** — nunca armazenadas em texto puro.
- **Autenticação e autorização** via Spring Security (todas as páginas exigem login).
- **Proteção CSRF** ativada (os formulários enviam o token automaticamente).
- **Validação de entrada** (Bean Validation) — “nunca confie no que o usuário envia”.
- **Prevenção de SQL Injection** — consultas parametrizadas via Spring Data JPA.
- **Segredos fora do código** — senha do banco em variável de ambiente; `.env` no `.gitignore`.
- **Mascaramento de dados sensíveis** — o cartão exibe apenas os 4 últimos dígitos.
- **`BigDecimal` para dinheiro** — evita erros de arredondamento do `double`.

Detalhes e o “porquê” de cada item estão na documentação em [docs/](docs/).

---

## 🧪 Testes

```bash
./mvnw test
```

Os testes em [`PagamentoFactoryTest`](src/test/java/br/edu/unievangelica/carteira/pagamento/PagamentoFactoryTest.java)
demonstram polimorfismo via interface, a fábrica e a implementação múltipla.

---

## 🗂️ Estrutura do projeto

```
src/main/java/br/edu/unievangelica/carteira/
├── pagamento/     → interfaces + implementações (Strategy, Factory) — o coração da aula
├── model/         → entidades JPA (Usuario, Transacao)
├── repository/    → repositórios Spring Data (padrão Repository)
├── service/       → regras de negócio (interface + implementação, DIP)
├── web/           → controllers e DTO (validação)
└── config/        → Spring Security e carga inicial de dados
```

---

## 📄 Licença

Distribuído sob a licença **MIT** — veja [LICENSE](LICENSE). Material educacional de livre uso.
