-- Garante que a tabela tutor existe antes de criar o relacionamento.
CREATE TABLE IF NOT EXISTS tutor (
    usuario_id BIGINT PRIMARY KEY REFERENCES usuario(id) ON DELETE CASCADE,
    telefone VARCHAR(20) NOT NULL DEFAULT ''
);

CREATE TABLE IF NOT EXISTS tutor_animal (
    tutor_id BIGINT NOT NULL REFERENCES tutor(usuario_id) ON DELETE CASCADE,
    animal_id BIGINT NOT NULL REFERENCES animal(id) ON DELETE CASCADE,
    PRIMARY KEY (tutor_id, animal_id)
);