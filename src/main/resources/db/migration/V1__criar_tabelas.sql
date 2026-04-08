-- 0. Tabela endereco
CREATE TABLE IF NOT EXISTS endereco(
    id         BIGSERIAL PRIMARY KEY,
    logradouro VARCHAR (225) NOT NULL,
    cidade     VARCHAR(100) NOT NULL,
    estado     VARCHAR(50) NOT NULL,
    cep        VARCHAR(20) NOT NULL
);



-- 1. Criar a tabela pai primeiro
CREATE TABLE IF NOT EXISTS usuario (
                                       id BIGSERIAL PRIMARY KEY,
                                       nome VARCHAR(100) NOT NULL,
                                       email VARCHAR(100) NOT NULL UNIQUE,
                                       senha VARCHAR(100) NOT NULL,
                                       tipo VARCHAR(50),
                                       endereco_id BIGINT,
                                       CONSTRAINT fk_endereco FOREIGN KEY (endereco_id) REFERENCES endereco(id)
);

CREATE TABLE IF NOT EXISTS cliente (
                                       usuario_id BIGINT PRIMARY KEY REFERENCES usuario(id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS admin (
                                     usuario_id BIGINT PRIMARY KEY REFERENCES usuario(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS tutor (
                                     usuario_id BIGINT PRIMARY KEY REFERENCES usuario(id) ON DELETE CASCADE,
    telefone VARCHAR(20)
    );

CREATE TABLE IF NOT EXISTS animal (
                                      id          BIGSERIAL PRIMARY KEY,
                                      nome        VARCHAR(100)  NOT NULL,
                                      especie     VARCHAR(100)  NOT NULL,
                                      raca        VARCHAR(100)  NOT NULL,
                                      idade_meses INTEGER       NOT NULL CHECK (idade_meses >= 0),
                                      sexo        VARCHAR(10)   NOT NULL,
                                      status      VARCHAR(20)   NOT NULL DEFAULT 'DISPONIVEL',
                                      vacinado    BOOLEAN       NOT NULL DEFAULT FALSE,
                                      porte       VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS adocoes (
                                       id             BIGSERIAL PRIMARY KEY,
                                       animal_id      BIGINT UNIQUE NOT NULL REFERENCES animal(id) ON DELETE RESTRICT,
                                       cliente_id     BIGINT        NOT NULL REFERENCES cliente(usuario_id) ON DELETE RESTRICT,
                                       data_adocao    DATE          NOT NULL DEFAULT CURRENT_DATE,
                                       termo_assinado BOOLEAN       NOT NULL DEFAULT FALSE
);