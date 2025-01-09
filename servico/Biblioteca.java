package servico;

import dominio.*;

import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private List<Livro> livros;
    private List<Usuario> usuarios;
    private List<Emprestimo> emprestimos;
    private Usuario usuarioLogado;

    public Biblioteca() {
        this.livros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.emprestimos = new ArrayList<>();
        this.usuarioLogado = null;
    }

    public void adicionarLivro(Livro livro) {
        livros.add(livro);
        System.out.println("Livro adicionado: " + livro.getTitulo());
    }

    public void listarLivros() {
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado!");
        } else {
            for (Livro livro : livros) {
                String status = livro.isDisponivel() ? "Disponível" : "Emprestado";
                System.out.println("Título: " + livro.getTitulo() + " | Autor: " + livro.getAutor() + " | Status: " + status);
            }
        }
    }

    public void realizarEmprestimo(int idUsuario, String tituloLivro) {
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

    public void devolverLivro(String tituloLivro) {
        Emprestimo emprestimo = emprestimos.stream()
                .filter(e -> e.getLivro().getTitulo().equalsIgnoreCase(tituloLivro) && !e.getLivro().isDisponivel())
                .findFirst()
                .orElse(null);

        if (emprestimo == null) {
            System.out.println("Livro não encontrado ou já devolvido.");
            return;
        }

        emprestimo.finalizarEmprestimo();
        System.out.println("Livro devolvido com sucesso!");
    }

    public void listarHistoricoUsuario() {
        if (usuarioLogado == null) {
            System.out.println("Nenhum usuário logado.");
            return;
        }

        List<Emprestimo> historico = emprestimos.stream()
                .filter(e -> e.getUsuario().equals(usuarioLogado))
                .toList();

        if (historico.isEmpty()) {
            System.out.println("Nenhum histórico encontrado para o usuário " + usuarioLogado.getNomeUsuario());
        } else {
            System.out.println("Histórico de empréstimos do usuário " + usuarioLogado.getNomeUsuario() + ":");
            for (Emprestimo emprestimo : historico) {
                System.out.println("- Livro: " + emprestimo.getLivro().getTitulo() + " | Data de empréstimo: " + emprestimo.getDataEmprestimo());
            }
        }
    }

    public void cadastrarUsuario(String nome, String senha) {
        Usuario usuario = new Usuario(nome, senha);
        usuarios.add(usuario);
        System.out.println("Usuário cadastrado com sucesso! ID: " + usuario.getId());
    }

    public boolean login(String nomeUsuario, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNomeUsuario().equals(nomeUsuario) && usuario.autenticar(senha)) {
                usuarioLogado = usuario;
                System.out.println("Login realizado com sucesso! Bem-vindo(a), " + usuarioLogado.getNomeUsuario());
                return true;
            }
        }
        System.out.println("Nome de usuário ou senha inválidos.");
        return false;
    }

    public void logout() {
        if (usuarioLogado != null) {
            System.out.println("Logout realizado. Até logo, " + usuarioLogado.getNomeUsuario() + "!");
            usuarioLogado = null;
        } else {
            System.out.println("Nenhum usuário está logado.");
        }
    }

    public boolean isUsuarioLogado() {
        return usuarioLogado != null;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
}
