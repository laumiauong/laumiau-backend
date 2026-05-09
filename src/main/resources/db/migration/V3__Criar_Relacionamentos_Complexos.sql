CREATE TABLE adocoes (
                         id BIGSERIAL PRIMARY KEY,
                         animal_id BIGINT UNIQUE NOT NULL REFERENCES animal(id) ON DELETE RESTRICT,
                         cliente_id BIGINT NOT NULL REFERENCES cliente(usuario_id) ON DELETE RESTRICT,
                         data_adocao DATE NOT NULL DEFAULT CURRENT_DATE,
                         termo_assinado BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE tutor_animal (
                              tutor_id BIGINT NOT NULL REFERENCES tutor(usuario_id) ON DELETE CASCADE,
                              animal_id BIGINT NOT NULL REFERENCES animal(id) ON DELETE CASCADE,
                              PRIMARY KEY (tutor_id, animal_id)
);