CREATE TABLE IF NOT EXISTS cliente (
    id BIGSERIAL      PRIMARY KEY,
    usuario_id BIGINT UNIQUE NOT NULL REFERENCES usuarios(id) ON DELETE RESTRICT
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
    cliente_id     BIGINT        NOT NULL REFERENCES cliente(id) ON DELETE RESTRICT,
    data_adocao    DATE          NOT NULL DEFAULT CURRENT_DATE,
    termo_assinado BOOLEAN       NOT NULL DEFAULT FALSE
    );