CREATE TABLE IF NOT EXISTS vacina (
                                      id             BIGSERIAL PRIMARY KEY,
                                      animal_id      BIGINT       NOT NULL REFERENCES animal(id) ON DELETE CASCADE,
    nome           VARCHAR(100) NOT NULL,
    data_aplicacao DATE         NOT NULL DEFAULT CURRENT_DATE,
    proxima_dose   DATE
    );