package com.biblioteca.entidades;

public class Livro {
    private int idLivro;
    private String titulo;
    private String autor;
    private boolean isDisponivel;

    public Livro(int idLivro, String titulo, String autor, boolean isDisponivel) {
        this.idLivro = idLivro;
        this.titulo = titulo;
        this.autor = autor;
        this.isDisponivel = isDisponivel;
    }

    public Livro(String titulo, String autor, boolean isDisponivel){
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

    public int getId() {
        return idLivro;
    }
}
