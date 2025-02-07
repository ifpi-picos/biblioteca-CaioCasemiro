package com.biblioteca.servico;

import com.biblioteca.banco.*;
import com.biblioteca.dao.*;
import com.biblioteca.entidades.*;
import java.sql.*;
import java.util.List;

public class Biblioteca {
    private UsuarioDAO usuarioDAO;
    private LivroDAO livroDAO;
    private EmprestimoDAO emprestimoDAO;
    private Notificacao notificacao;
    
    public Biblioteca(Notificacao notificacao){
        Connection conexao = Conexaobd.conexao();
        this.usuarioDAO = new UsuarioDAO(conexao);
        this.livroDAO = new LivroDAO(conexao);
        this.emprestimoDAO = new EmprestimoDAO(conexao);
        this.notificacao = notificacao;
    }

    public void adicionarLivro(Usuario usuarioLogado, String titulo, String autor) {
        if (!usuarioLogado.isAdmin()) {
            notificacao.enviarNotificacao("Apenas administradores podem adicionar livros.");
            return;
        }
        Livro livro = new Livro(titulo, autor, true);
        livroDAO.inserirLivro(livro);
        notificacao.enviarNotificacao("Livro adicionado: " + titulo);
    }

    public void listarLivros(){
        List<Livro> livros = livroDAO.listarLivros();
        if(livros.isEmpty()){
            notificacao.enviarNotificacao("Nenhum livro cadastrado");
        } else {
            for(Livro livro : livros){
                String status = livro.isDisponivel() ? "Disponível" : "Emprestado";
                notificacao.enviarNotificacao("Id: " + livro.getId() + " | Título: " + livro.getTitulo() + " | Autor: " + livro.getAutor()
                + " | Status: " + status);
            }
        }
    }

    public void cadastrarUsuario(String nome, String senha){
        usuarioDAO.inserirUsuario(nome, senha, false);
        notificacao.enviarNotificacao("Usuario cadastrado com sucesso");
    }

    public Boolean login(String nome, String senha){
        Usuario usuario = usuarioDAO.buscarUsuario(nome, senha);
        if(usuario != null){
            notificacao.enviarNotificacao("Login efetuado! Bem-vindo(a), " + usuario.getNomeUsuario());
            return true;
        } else {
            notificacao.enviarNotificacao("Usuário ou senha inválidos");
            return false;
        }
    }

    public void realizarEmprestimo(int idUsuario, int idLivro){
        Usuario usuario = usuarioDAO.buscarUsuarioPorId(idUsuario);
        Livro livro = livroDAO.buscarLivroPorId(idLivro);

        if(usuario == null || livro == null || !livro.isDisponivel()){
            notificacao.enviarNotificacao("Emprestimo não realizado. Verifique se o usuário e o livro existem ou o livro está disponível");
            return;
        }

        Emprestimo emprestimo = new Emprestimo(usuario, livro);
        int idEmprestimo = emprestimoDAO.cadastrarEmprestimo(emprestimo);
        livroDAO.atualizarDisponibilidade(idLivro, false);
        notificacao.enviarNotificacao("Emprestimo realizado com sucesso! id do emprestimo: " + idEmprestimo);

    }

    public void devolverLivros(int idemprestimo){
        Emprestimo emprestimo = emprestimoDAO.buscarEmprestimoPorId(idemprestimo);

        if (emprestimo == null){
            notificacao.enviarNotificacao("Nenhum emprestimo encontrado para esse livro");
            return;
        } 
        int idLivro = emprestimo.getLivro().getId();
        emprestimoDAO.finalizarEmprestimo(idemprestimo);
        livroDAO.atualizarDisponibilidade(idLivro, true);
        notificacao.enviarNotificacao("Livro devolvido");
    }
}
