package com.biblioteca.entidades;

public class Usuario {
    private int idUsuario;
    private String nomeUsuario;
    private String senha;
    private Boolean isAdmin;

    public Usuario(int idUsuario , String nomeUsuario, String senha,Boolean isAdmin) {
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.idUsuario = idUsuario;
        this.isAdmin = isAdmin;
    }

    public Usuario(String nomeUsuario, String senha){
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.isAdmin = false;
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

    public boolean isAdmin(){
        return this.isAdmin;
    }
}
