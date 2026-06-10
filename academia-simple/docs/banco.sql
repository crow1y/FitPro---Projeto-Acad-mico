-- Cria o banco de dados
CREATE DATABASE IF NOT EXISTS academia_db;
USE academia_db;

-- Tabela de usuários do sistema (login/senha para autenticação)
CREATE TABLE usuario (
    id      VARCHAR(50)  NOT NULL,
    login   VARCHAR(50)  NOT NULL UNIQUE,
    senha   VARCHAR(255) NOT NULL,
    funcao  VARCHAR(50)  NOT NULL,
    PRIMARY KEY (id)
);

-- Tabela de planos da academia (ex: Mensal, Trimestral, Anual)
CREATE TABLE plano (
    id     BIGINT       NOT NULL AUTO_INCREMENT,
    nome   VARCHAR(100) NOT NULL,
    valor  DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id)
);

-- Tabela de alunos matriculados
CREATE TABLE aluno (
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    nome      VARCHAR(100) NOT NULL,
    email     VARCHAR(100) NOT NULL UNIQUE,
    telefone  VARCHAR(20),
    ativo     BOOLEAN      DEFAULT TRUE,
    plano_id  BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (plano_id) REFERENCES plano(id)
);

-- Tabela de check-ins (registro de entrada dos alunos)
CREATE TABLE checkin (
    id        BIGINT   NOT NULL AUTO_INCREMENT,
    aluno_id  BIGINT   NOT NULL,
    data_hora DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (aluno_id) REFERENCES aluno(id)
);


