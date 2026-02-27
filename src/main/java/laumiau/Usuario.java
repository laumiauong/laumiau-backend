package laumiau;

public class Usuario {

    public enum TipoUsuario {
        ADMIN,
        CLIENTE
    }

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private TipoUsuario tipoUsuario;

    public Usuario() {
    }
}
