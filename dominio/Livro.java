package dominio;

public class Livro {
    private String titulo;
    private String autor;
    private String genero;
    private boolean isDisponivel;

    public Livro(String titulo, String autor, String genero, boolean isDisponivel){
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.isDisponivel = isDisponivel;
    }

    public void setDisponivel(boolean disponivel){
        this.isDisponivel = disponivel;
    }

    public String getTitulo(){
        return titulo;
    }

    public String getAutor(){
        return autor;
    }

    public String getGenero(){
        return genero;
    }

    public boolean isDisponivel(){
        return isDisponivel;
    }
}