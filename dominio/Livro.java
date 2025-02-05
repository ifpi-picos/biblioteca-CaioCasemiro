package dominio;

public class Livro {
    private String titulo;
    private String autor;
    private boolean isDisponivel;

    public Livro(String titulo, String autor, boolean isDisponivel) {
        this.titulo = titulo;
        this.autor = autor;
        this.isDisponivel = isDisponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.isDisponivel = disponivel;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public boolean isDisponivel() {
        return isDisponivel;
    }
}
