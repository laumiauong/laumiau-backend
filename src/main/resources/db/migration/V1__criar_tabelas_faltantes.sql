CREATE TABLE usuario (
                         id BIGSERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         senha VARCHAR(100) NOT NULL,
                         tipo VARCHAR(20) NOT NULL
);

CREATE TABLE cliente (
                         usuario_id BIGINT PRIMARY KEY REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE TABLE admin (
                       usuario_id BIGINT PRIMARY KEY REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE TABLE tutor (
                       usuario_id BIGINT PRIMARY KEY REFERENCES usuario(id) ON DELETE CASCADE,
                       telefone VARCHAR(20) NOT NULL
);

CREATE TABLE endereco (
                          id BIGSERIAL PRIMARY KEY,
                          logradouro VARCHAR(255) NOT NULL,
                          cidade VARCHAR(100) NOT NULL,
                          estado CHAR(2) NOT NULL,
                          cep VARCHAR(10) NOT NULL,
                          usuario_id BIGINT UNIQUE REFERENCES usuario(id) ON DELETE CASCADE
);