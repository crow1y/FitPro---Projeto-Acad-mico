# 🏋️ Academia FitPro

**N2 — Desenvolvimento de Aplicações WEB | ADS1241 | PUC Goiás**

---

## Tecnologias

- **Back-end:** Java 21 + Spring Boot + Spring Security + JWT
- **Banco de dados:** MySQL
- **Front-end:** HTML + Bootstrap 5 + JavaScript

---

## Diagrama do Banco de Dados

```
plano                        usuario
─────────────────            ────────────────────
id   (PK)                    id     (PK, UUID)
nome                         login  (único)
valor                        senha  (criptografada)
                             funcao (ADMIN ou USER)

aluno
────────────────────
id        (PK)
nome
email     (único)
telefone
ativo     (true/false)
plano_id  (FK → plano)

checkin
────────────────────
id        (PK)
aluno_id  (FK → aluno)
data_hora
```

**Relacionamentos:**
- Um `plano` pode ter vários `alunos` (1:N)
- Um `aluno` pode ter vários `checkins` (1:N)

---

## Como rodar

### 1. Banco de dados
```bash
mysql -u root -p < docs/banco.sql
```

### 2. Back-end
Edite o arquivo `backend/src/main/resources/application.properties`:
```properties
spring.datasource.password=SUA_SENHA_AQUI
```

Depois rode:
```bash
cd backend
mvn spring-boot:run
```

### 3. Front-end
Abra `frontend/index.html` no navegador (ou use Live Server no VSCode).

### 4. Criar o primeiro usuário admin
No Postman ou Insomnia, faça uma requisição:
```
POST http://localhost:8080/auth/registrar-admin
Content-Type: application/json

{ "login": "admin", "senha": "123456" }
```

---

## Endpoints da API

| Método | Endpoint | Acesso | Descrição |
|--------|----------|--------|-----------|
| POST | /auth/login | público | faz login, retorna token |
| POST | /auth/registrar | público | cria usuário comum |
| POST | /auth/registrar-admin | público | cria admin (bloqueie após o setup) |
| GET | /alunos | autenticado | lista alunos |
| POST | /alunos | ADMIN | cria aluno |
| PUT | /alunos/{id} | ADMIN | edita aluno |
| DELETE | /alunos/{id} | ADMIN | inativa aluno |
| GET | /planos | autenticado | lista planos |
| POST | /planos | ADMIN | cria plano |
| DELETE | /planos/{id} | ADMIN | exclui plano |
| GET | /checkins | autenticado | lista check-ins |
| POST | /checkins?alunoId=1 | autenticado | registra check-in |

---

## Estrutura do projeto

```
academia-simple/
├── backend/
│   ├── pom.xml
│   └── src/main/java/com/academia/
│       ├── AcademiaApplication.java
│       ├── config/
│       │   ├── ConfiguracaoSeguranca.java  ← regras de segurança
│       │   └── FiltroSeguranca.java        ← verifica o token JWT
│       ├── controller/
│       │   ├── AuthController.java         ← login e registro
│       │   ├── AlunoController.java
│       │   ├── PlanoController.java
│       │   └── CheckinController.java
│       ├── dto/
│       │   ├── LoginDTO.java
│       │   ├── TokenDTO.java
│       │   ├── AlunoDTO.java
│       │   └── PlanoDTO.java
│       ├── model/
│       │   ├── Usuario.java
│       │   ├── Aluno.java
│       │   ├── Plano.java
│       │   └── Checkin.java
│       ├── repository/
│       │   ├── UsuarioRepository.java
│       │   ├── AlunoRepository.java
│       │   ├── PlanoRepository.java
│       │   └── CheckinRepository.java
│       └── service/
│           ├── AutorizacaoService.java     ← carrega usuário pro Spring Security
│           └── TokenService.java           ← gera e valida JWT
├── frontend/
│   ├── index.html      ← tela de login
│   └── dashboard.html  ← sistema principal
└── docs/
    └── banco.sql       ← script do banco de dados
```
