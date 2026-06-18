package laumiau.model;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "solicitacao_adocao")
public class SolicitacaoAdocao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "adocao_id", nullable = false)
    private Adocoes adocao;

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "cpf", length = 14)
    private String cpf;

    @Column(name = "data_nascimento")
    private String dataNascimento;

    @Column(name = "profissao")
    private String profissao;


    @Column(name = "tipo_moradia")
    private String tipoMoradia;

    @Column(name = "possui_quintal")
    private String possuiQuintal;


    @Column(name = "teve_pets_antes")
    private String tevePetsAntes;

    @Column(name = "outros_pets", columnDefinition = "TEXT")
    private String outrosPets;

    @Column(name = "motivo_adocao", columnDefinition = "TEXT")
    private String motivoAdocao;

    @Column(name = "data_envio", nullable = false)
    private LocalDate dataEnvio;

    public SolicitacaoAdocao() {}

    public SolicitacaoAdocao(Adocoes adocao,
                             String telefone, String cpf,
                             String dataNascimento, String profissao,
                             String tipoMoradia, String possuiQuintal,
                             String tevePetsAntes, String outrosPets,
                             String motivoAdocao) {
        this.adocao         = adocao;
        this.telefone       = telefone;
        this.cpf            = cpf;
        this.dataNascimento = dataNascimento;
        this.profissao      = profissao;
        this.tipoMoradia    = tipoMoradia;
        this.possuiQuintal  = possuiQuintal;
        this.tevePetsAntes  = tevePetsAntes;
        this.outrosPets     = outrosPets;
        this.motivoAdocao   = motivoAdocao;
        this.dataEnvio      = LocalDate.now();
    }

    public Long getId()                     { return id; }
    public Adocoes getAdocao()              { return adocao; }
    public void setAdocao(Adocoes a)        { this.adocao = a; }
    public String getTelefone()             { return telefone; }
    public void setTelefone(String v)       { this.telefone = v; }
    public String getCpf()                  { return cpf; }
    public void setCpf(String v)            { this.cpf = v; }
    public String getDataNascimento()       { return dataNascimento; }
    public void setDataNascimento(String v) { this.dataNascimento = v; }
    public String getProfissao()            { return profissao; }
    public void setProfissao(String v)      { this.profissao = v; }
    public String getTipoMoradia()          { return tipoMoradia; }
    public void setTipoMoradia(String v)    { this.tipoMoradia = v; }
    public String getPossuiQuintal()        { return possuiQuintal; }
    public void setPossuiQuintal(String v)  { this.possuiQuintal = v; }
    public String getTevePetsAntes()        { return tevePetsAntes; }
    public void setTevePetsAntes(String v)  { this.tevePetsAntes = v; }
    public String getOutrosPets()           { return outrosPets; }
    public void setOutrosPets(String v)     { this.outrosPets = v; }
    public String getMotivoAdocao()         { return motivoAdocao; }
    public void setMotivoAdocao(String v)   { this.motivoAdocao = v; }
    public LocalDate getDataEnvio()         { return dataEnvio; }
}