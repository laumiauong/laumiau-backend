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
        setIdade(idade);
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

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getEspecie(){
        return especie;
    }

    public void setEspecie(String especie){
        this.especie = especie;
    }

    public String getRaca(){
        return raca;
    }

    public void setRaca(String raca){
        this.raca = raca;
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

    @Override
    public String toString(){
       return "ID: "+ id +
       "\nNome: " + nome +
       "\nEspécie: " + especie +
       "\nRaça: " + raca +
       "\nIdade: " + idade +
       "\nSexo: " + sexo +
       "\nAdotado: " + (adotado ? "Sim" : "Não") +
       "\nVacinado: " + (vacinado ? "Sim" : "Não") +
       "\nPorte: " + porte +
       "\n---------------------------------";
    }

}
