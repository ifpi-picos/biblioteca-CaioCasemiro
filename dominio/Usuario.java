package dominio;

public class Usuario {
    int idUsuario;
    String nomeUsuario;
    private static int contadorDeId = 1;

    public Usuario(String nomeUsuario){
        this.nomeUsuario = nomeUsuario;
        this.idUsuario = contadorDeId++;
    }

    public String getNomeUsuario(){
        return nomeUsuario;
    }

    public int getId(){
        return idUsuario;
    }
}
