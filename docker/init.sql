CREATE DATABASE IF NOT EXISTS fj21;
USE fj21;

CREATE TABLE IF NOT EXISTS tarefas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descricao VARCHAR(255),
    finalizado BOOLEAN DEFAULT FALSE,
    dataFinalizacao DATE
);

CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(255),
    senha VARCHAR(255)
);

INSERT INTO usuarios (login, senha) VALUES ('admin', 'admin');
