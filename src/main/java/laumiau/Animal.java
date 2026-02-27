package laumiau;

public class Animal {

    private static long contadorId = 1;

    private long id;
    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private String sexo;
    private boolean adotado;
    private String vacinado;
    private String porte;

    public Animal(String nome, String especie, String raca, int idade, String sexo, boolean adotado, String vacinado, String porte) {
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public long getId() {
        return id;
    }

    public boolean isAdotado() {
        return adotado;
    }

    public String getVacinado(){
        return vacinado;
    }

    public void setVacinado(String vacinado){
        this.vacinado = vacinado;
    }

    public String getPorte(){
        return porte;
    }

    public void setPorte(String porte){
        this.porte = porte;
    }

    public void adotar() {
        this.adotado = true;
    }

    public void exibirDados(){
        System.out.println("ID: "+ id);
        System.out.println("Nome: "+ nome);
        System.out.println("Espécie: "+ especie);
        System.out.println("Raça: "+ raca);
        System.out.println("Idade: "+ idade);
        System.out.println("Sexo: "+ sexo);
        System.out.println("Adotado: "+ (adotado ? "Sim" : "Não"));
        System.out.println("Vacinado: "+ vacinado);
        System.out.println("Porte: "+ porte);
    }

}
