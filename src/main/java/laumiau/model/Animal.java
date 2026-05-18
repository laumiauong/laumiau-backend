package laumiau.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String especie;

    @Column(nullable = false)
    private String raca;

    @Column(name = "idade_meses", nullable = false)
    private int idade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAnimal status = StatusAnimal.DISPONIVEL;

    @Column(nullable = false)
    private boolean vacinado = false;

    @Enumerated(EnumType.STRING)
    private Porte porte;

    @Column(name = "caminho_foto")
    private String caminhoFoto;

    @Column(name = "cor")
    private String cor;

    @Column(name = "peso")
    private String peso;

    @Column(name = "responsavel")
    private String responsavel;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @OneToMany(mappedBy = "animal")
    private List<Vacina> vacinas = new ArrayList<>();

    @ManyToMany(mappedBy = "animaisInteresse")
    private List<Tutor> tutoresInteressados = new ArrayList<>();

    public Animal() {}

    // Construtor sem caminhoFoto
    public Animal(String nome, String especie, String raca, int idade, Sexo sexo, boolean vacinado, Porte porte) {
        setNome(nome);
        setEspecie(especie);
        setRaca(raca);
        setIdade(idade);
        setSexo(sexo);
        this.status = StatusAnimal.DISPONIVEL;
        this.vacinado = vacinado;
        this.porte = porte;
    }

    // Construtor com caminhoFoto
    public Animal(String nome, String especie, String raca, int idade, Sexo sexo, boolean vacinado, Porte porte, String caminhoFoto) {
        setNome(nome);
        setEspecie(especie);
        setRaca(raca);
        setIdade(idade);
        setSexo(sexo);
        this.status = StatusAnimal.DISPONIVEL;
        this.vacinado = vacinado;
        this.porte = porte;
        this.caminhoFoto = caminhoFoto;
    }

    public Long getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        this.nome = nome;
    }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) {
        if (especie == null || especie.trim().isEmpty()) {
            throw new IllegalArgumentException("Espécie é obrigatória.");
        }
        this.especie = especie;
    }

    public String getRaca() { return raca; }
    public void setRaca(String raca) {
        if (raca == null || raca.trim().isEmpty()) {
            throw new IllegalArgumentException("Raça é obrigatória.");
        }
        this.raca = raca;
    }

    public int getIdade() { return idade; }
    public void setIdade(int idade) {
        if (idade >= 0) {
            this.idade = idade;
        } else {
            throw new IllegalArgumentException("Idade não pode ser negativa.");
        }
    }

    public Sexo getSexo() { return sexo; }
    public void setSexo(Sexo sexo) {
        if (sexo == null) {
            throw new IllegalArgumentException("Sexo é obrigatório.");
        }
        this.sexo = sexo;
    }

    public StatusAnimal getStatus() { return status; }
    public void setStatus(StatusAnimal status) {
        if (status == null) {
            throw new IllegalArgumentException("Status não pode ser nulo.");
        }
        this.status = status;
    }

    public boolean isVacinado() { return vacinado; }

    // 👇 MÉTODO ADICIONADO AQUI PARA RESOLVER O ERRO 👇
    public void setVacinado(boolean vacinado) { this.vacinado = vacinado; }

    public void vacinar() { this.vacinado = true; }

    public Porte getPorte() { return porte; }
    public void setPorte(Porte porte) { this.porte = porte; }

    public String getCaminhoFoto() { return caminhoFoto; }
    public void setCaminhoFoto(String caminhoFoto) { this.caminhoFoto = caminhoFoto; }

    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }

    public String getPeso() { return peso; }
    public void setPeso(String peso) { this.peso = peso; }

    public String getResponsavel() { return responsavel; }
    public void setResponsavel(String responsavel) { this.responsavel = responsavel; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public List<Vacina> getVacinas() { return vacinas; }
    public void adicionarVacina(Vacina vacina) { this.vacinas.add(vacina); }

    public boolean isAdotado() {
        return status == StatusAnimal.ADOTADO;
    }

    public void adotar() {
        if (status == StatusAnimal.ADOTADO) {
            throw new IllegalStateException("Esse animal já foi adotado.");
        }
        this.status = StatusAnimal.ADOTADO;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                "\nNome: " + nome +
                "\nEspécie: " + especie +
                "\nRaça: " + raca +
                "\nIdade: " + idade + " meses" +
                "\nSexo: " + sexo +
                "\nStatus: " + status +
                "\nVacinado: " + (vacinado ? "Sim" : "Não") +
                "\nPorte: " + (porte != null ? porte : "Não informado") +
                "\nCor: " + (cor != null ? cor : "Não informada") +
                "\nPeso: " + (peso != null ? peso : "Não informado") +
                "\nFoto: " + (caminhoFoto != null ? caminhoFoto : "Sem foto") +
                "\n---------------------------------";
    }
}