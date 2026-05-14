-- Tabela de endereços vinculada ao usuário
CREATE TABLE IF NOT EXISTS endereco (
                                        id BIGSERIAL PRIMARY KEY,
                                        logradouro VARCHAR(255) NOT NULL,
                                        cidade VARCHAR(100) NOT NULL,
                                        estado CHAR(2) NOT NULL,
                                        cep VARCHAR(10) NOT NULL,
                                        usuario_id BIGINT UNIQUE REFERENCES usuario(id) ON DELETE CASCADE
);

-- Tabela de adoções vinculada ao animal e ao cliente
CREATE TABLE IF NOT EXISTS adocoes (
                                       id BIGSERIAL PRIMARY KEY,
                                       animal_id BIGINT UNIQUE NOT NULL REFERENCES animal(id) ON DELETE RESTRICT,
                                       cliente_id BIGINT NOT NULL REFERENCES cliente(usuario_id) ON DELETE RESTRICT,
                                       data_adocao DATE NOT NULL DEFAULT CURRENT_DATE,
                                       termo_assinado BOOLEAN NOT NULL DEFAULT FALSE
);

-- Tabela de vacinas
CREATE TABLE IF NOT EXISTS vacina (
                                      id BIGSERIAL PRIMARY KEY,
                                      animal_id BIGINT NOT NULL REFERENCES animal(id) ON DELETE CASCADE,
                                      nome VARCHAR(100) NOT NULL,
                                      data_aplicacao DATE NOT NULL,
                                      proxima_dose DATE
);