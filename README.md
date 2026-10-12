# Carteira Fintech — Interfaces em Java

Projeto didático da **Semana 11** da disciplina de **Programação Orientada a Objetos** (UniEVANGÉLICA).
Uma mini carteira de pagamentos (Pix, cartão e boleto) que demonstra, na prática, o conteúdo da aula:
**interface como contrato, implementação múltipla e “programar para a interface”** — aplicando boas
práticas de mercado com **Design Patterns, SOLID, Clean Code e Segurança da Informação**.

> **Stack:** Java 17 · Spring Boot 3.3 · Spring MVC · Thymeleaf · Spring Security · Spring Data JPA · PostgreSQL (com H2 para testes rápidos).

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

## 🚀 Como executar

### Opção A — rápida, sem instalar nada (banco H2 em memória)

Requer apenas o **Java 17+** instalado.

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=h2
```

Acesse **http://localhost:8080** e entre com:

- **usuário:** `aluno`
- **senha:** `senha123`

> No Windows, use `mvnw.cmd` no lugar de `./mvnw`. Na primeira execução, o wrapper baixa o Maven automaticamente.

### Opção B — com PostgreSQL (mais próximo do mundo real)

1. Suba o banco com Docker:

   ```bash
   docker compose up -d
   ```

2. Copie o arquivo de exemplo de variáveis e ajuste se quiser:

   ```bash
   cp .env.example .env
   ```

3. Rode a aplicação (as variáveis do `.env` precisam estar no ambiente; veja a nota abaixo):

   ```bash
   ./mvnw spring-boot:run
   ```

> **Carregando o `.env`:** o Spring Boot não lê `.env` sozinho. No terminal, você pode exportar as
> variáveis antes de rodar, por exemplo: `export $(grep -v '^#' .env | xargs)` (Linux/macOS).
> Os *defaults* já combinam com o `docker-compose.yml`, então a Opção B também funciona sem o `.env`.

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
