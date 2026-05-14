CREATE TABLE tutor_animal (
    tutor_id  BIGINT NOT NULL REFERENCES tutor(usuario_id) ON DELETE RESTRICT,
    animal_id BIGINT NOT NULL REFERENCES animal(id) ON DELETE RESTRICT,
    PRIMARY KEY (tutor_id, animal_id)
);