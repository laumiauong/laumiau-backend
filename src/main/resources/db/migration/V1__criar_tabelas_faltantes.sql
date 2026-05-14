-- 1. Criar a tabela pai primeiro
CREATE TABLE IF NOT EXISTS usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL
);

-- 2. Agora as tabelas que dependem do usuario
CREATE TABLE IF NOT EXISTS cliente (
    usuario_id BIGINT PRIMARY KEY REFERENCES usuario(id) ON DELETE RESTRICT
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