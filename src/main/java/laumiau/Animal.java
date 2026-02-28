package laumiau;

public class Animal {

    private static long contadorId = 1;

    private long id;
    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private Sexo sexo;
    private boolean adotado;
    private boolean vacinado;
    private Porte porte;

    public Animal(String nome, String especie, String raca, int idade, Sexo sexo, boolean vacinado, Porte porte) {
        this.id = contadorId++;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.sexo = sexo;
        this.adotado = false;
        this.vacinado = vacinado;
        this.porte = porte;
    }

    public long getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public String getEspecie(){
        return especie;
    }

    public String getRaca(){
        return raca;
    }

    public int getIdade(){
        return idade;
    }

    public void setIdade(int idade){
        if(idade >= 0){
            this.idade = idade;
        } else{
            System.out.println("Idade inválida!");
        }
    }

    public Sexo getSexo(){
        return sexo;
    }

    public boolean isAdotado(){
        return adotado;
    }

    public boolean isVacinado(){
        return vacinado;
    }

    public Porte getPorte(){
        return porte;
    }

    public void adotar() {
        if(!adotado){
            this.adotado = true;
            System.out.println("Animal adotado!");
        } else {
            System.out.println("Esse animal já foi adotado!");
        }
    }

    public void vacinar(){
        this.vacinado = true;
    }

    public void exibirDados(){
        System.out.println("ID: "+ id);
        System.out.println("Nome: "+ nome);
        System.out.println("Espécie: "+ especie);
        System.out.println("Raça: "+ raca);
        System.out.println("Idade: "+ idade);
        System.out.println("Sexo: "+ sexo);
        System.out.println("Adotado: "+ (adotado ? "Sim" : "Não"));
        System.out.println("Vacinado: "+ (vacinado ? "Sim" : "Não"));
        System.out.println("Porte: "+ porte);
    }

}
