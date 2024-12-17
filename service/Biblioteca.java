package service;
import dominio.*;

import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private List<Livro> livros;
    private List<Usuario> usuarios;
    private List<Emprestimo> emprestimos;

    public void bibliotecaService(){
        this.livros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
    }

    public void adicionarLivro(Livro livro){
        livros.add(livro);
        System.out.println("Livro adicionado: "+ livro.getTitulo());
    }

    public void adicionarUsuario(Usuario usuario){
        usuarios.add(usuario);
        System.out.println("Usuário adicionado:"+ usuario.getNomeUsuario());
    }

    public void realizarEmprestimo(int idUsuario, String tituloLivro){
        Usuario usuario = usuarios.stream()
            .filter(u -> u.getId() == idUsuario)
            .findFirst()
            .orElse(null);

            Livro livro = livros.stream()
            .filter(l -> l.getTitulo().equalsIgnoreCase(tituloLivro) && l.isDisponivel())
            .findFirst()
            .orElse(null);

    if (usuario == null || livro == null) {
        System.out.println("Usuário ou livro não encontrado ou livro indisponível.");
        return;
    }

    Emprestimo emprestimo = new Emprestimo(emprestimos.size() + 1, usuario, livro);
    emprestimos.add(emprestimo);
    System.out.println("Empréstimo realizado: " + livro.getTitulo() + " para " + usuario.getNomeUsuario());
    }
    
}
