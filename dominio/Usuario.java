package dominio;

public class Usuario {
    private int idUsuario;
    private String nomeUsuario;
    private String senha;
    private static int contadorDeId = 1;

    public Usuario(String nomeUsuario, String senha) {
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.idUsuario = contadorDeId++;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public int getId() {
        return idUsuario;
    }

    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }
}
