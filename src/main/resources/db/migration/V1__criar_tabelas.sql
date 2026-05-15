-- Criar tabela pai de usuários
CREATE TABLE usuario (
                         id BIGSERIAL PRIMARY KEY,
                         nome VARCHAR(100) NOT NULL,
                         email VARCHAR(100) NOT NULL UNIQUE,
                         senha VARCHAR(100) NOT NULL,
                         tipo VARCHAR(20) NOT NULL
);

-- Criar tabelas filhas (especializações)
CREATE TABLE admin (
                       usuario_id BIGINT PRIMARY KEY REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE TABLE cliente (
                         usuario_id BIGINT PRIMARY KEY REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE TABLE tutor (
                       usuario_id BIGINT PRIMARY KEY REFERENCES usuario(id) ON DELETE CASCADE,
                       telefone VARCHAR(20) NOT NULL
);

-- Criar tabela de animais
CREATE TABLE animal (
                        id BIGSERIAL PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        especie VARCHAR(100) NOT NULL,
                        raca VARCHAR(100) NOT NULL,
                        idade_meses INTEGER NOT NULL CHECK (idade_meses >= 0),
                        sexo VARCHAR(10) NOT NULL,
                        status VARCHAR(20) NOT NULL DEFAULT 'DISPONIVEL',
                        vacinado BOOLEAN NOT NULL DEFAULT FALSE,
                        porte VARCHAR(10)
);

-- Inserir Admin inicial para você conseguir logar
INSERT INTO usuario (nome, email, senha, tipo)
VALUES ('Admin LauMiau', 'admin@laumiau.com', 'admin123', 'admin');

INSERT INTO admin (usuario_id)
SELECT id FROM usuario WHERE email = 'admin@laumiau.com';