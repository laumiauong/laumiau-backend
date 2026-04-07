CREATE OR REPLACE FUNCTION atualizar_status_animal()
RETURNS TRIGGER AS $$
BEGIN
UPDATE animal
SET status = 'ADOTADO'
WHERE id = NEW.animal_id;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trigger_adocao_inserida ON adocoes;
CREATE TRIGGER trigger_adocao_inserida
    AFTER INSERT ON adocoes
    FOR EACH ROW
    EXECUTE FUNCTION atualizar_status_animal();

CREATE OR REPLACE FUNCTION reverter_status_animal()
RETURNS TRIGGER AS $$
BEGIN
UPDATE animal
SET status = 'DISPONIVEL'
WHERE id = OLD.animal_id;
RETURN OLD;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trigger_adocao_deletada ON adocoes;
CREATE TRIGGER trigger_adocao_deletada
    AFTER DELETE ON adocoes
    FOR EACH ROW
    EXECUTE FUNCTION reverter_status_animal();

CREATE OR REPLACE FUNCTION validar_idade_animal()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.idade_meses < 0 THEN
        RAISE EXCEPTION 'Idade do animal não pode ser negativa!';
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trigger_validar_idade ON animal;
CREATE TRIGGER trigger_validar_idade
    BEFORE INSERT OR UPDATE ON animal
                         FOR EACH ROW
                         EXECUTE FUNCTION validar_idade_animal();