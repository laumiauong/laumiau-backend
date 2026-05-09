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

CREATE TABLE vacina (
                        id BIGSERIAL PRIMARY KEY,
                        animal_id BIGINT NOT NULL REFERENCES animal(id) ON DELETE CASCADE,
                        nome VARCHAR(100) NOT NULL,
                        data_aplicacao DATE NOT NULL,
                        proxima_dose DATE
);