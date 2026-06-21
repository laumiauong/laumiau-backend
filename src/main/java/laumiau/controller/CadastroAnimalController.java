package laumiau.controller;

import laumiau.model.Animal;
import laumiau.model.Porte;
import laumiau.model.Sexo;
import laumiau.service.AnimalService;

public class CadastroAnimalController {

    private final AnimalService animalService;

    public CadastroAnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }


    public String cadastrar(String nome, String especie, String raca,
                            String idadeTxt, String sexo, String porteSel,
                            String peso, String cor, String responsavel,
                            String descricao, boolean vacinado,
                            String caminhoFoto) {
        try {
            validar(nome, especie, raca, idadeTxt, sexo);

            Animal animal = new Animal(
                    nome, especie, raca,
                    Integer.parseInt(idadeTxt),
                    sexo.equals("Macho") ? Sexo.MACHO : Sexo.FEMEA,
                    vacinado,
                    parsePorte(porteSel),
                    caminhoFoto
            );
            animal.setPeso(limpar(peso));
            animal.setCor(limpar(cor));
            animal.setResponsavel(limpar(responsavel));
            animal.setDescricao(limpar(descricao));

            animalService.cadastrar(animal);
            return null;

        } catch (NumberFormatException e) {
            return "Digite apenas números no campo Idade.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }


    public String atualizar(Animal animal, String nome, String especie, String raca,
                            String idadeTxt, String sexo, String porteSel,
                            String peso, String cor, String responsavel,
                            String descricao, boolean vacinado,
                            String caminhoFoto) {
        try {
            validar(nome, especie, raca, idadeTxt, sexo);

            animal.setNome(nome);
            animal.setEspecie(especie);
            animal.setRaca(raca);
            animal.setIdade(Integer.parseInt(idadeTxt));
            animal.setSexo(sexo.equals("Macho") ? Sexo.MACHO : Sexo.FEMEA);
            animal.setPorte(parsePorte(porteSel));
            animal.setVacinado(vacinado);
            animal.setPeso(limpar(peso));
            animal.setCor(limpar(cor));
            animal.setResponsavel(limpar(responsavel));
            animal.setDescricao(limpar(descricao));
            animal.setCaminhoFoto(caminhoFoto);

            animalService.atualizar(animal);
            return null;

        } catch (NumberFormatException e) {
            return "Digite apenas números no campo Idade.";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }



    private void validar(String nome, String especie, String raca,
                         String idadeTxt, String sexo) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Preencha o campo: Nome do animal.");
        if (especie == null || especie.equals("Selecione..."))
            throw new IllegalArgumentException("Selecione a espécie.");
        if (raca == null || raca.isBlank())
            throw new IllegalArgumentException("Preencha o campo: Raça.");
        if (idadeTxt == null || idadeTxt.isBlank())
            throw new IllegalArgumentException("Preencha o campo: Idade.");
        if (sexo == null || sexo.equals("Selecione..."))
            throw new IllegalArgumentException("Selecione o sexo.");
    }

    private Porte parsePorte(String porteSel) {
        if (porteSel == null || porteSel.equals("Selecione...")) {
            return null;
        }

        return switch (porteSel.trim().toUpperCase()) {
            case "PEQUENO" -> Porte.PEQUENO;
            case "MEDIO" -> Porte.MEDIO;
            case "GRANDE" -> Porte.GRANDE;
            default -> null;
        };
    }

    private String limpar(String valor) {
        if (valor == null || valor.startsWith("Ex:")) return "";
        return valor.trim();
    }
}
