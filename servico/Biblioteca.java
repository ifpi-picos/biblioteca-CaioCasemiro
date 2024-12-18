package servico;

import dominio.*;

import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
        private List<Livro> livros;
        private List<Usuario> usuarios;
        private List<Emprestimo> emprestimos;
    
        public Biblioteca() {
            this.livros = new ArrayList<>();
            this.usuarios = new ArrayList<>();
            this.emprestimos = new ArrayList<>();
        }
    
        public void adicionarLivro(Livro livro) {
            livros.add(livro);
            System.out.println("Livro adicionado: " + livro.getTitulo());
        }
    
        public void adicionarUsuario(Usuario usuario) {
            usuarios.add(usuario);
            System.out.println("Usuário adicionado: " + usuario.getNomeUsuario());
            System.out.println("Seu ID é: " + usuario.getId());
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
    
        public void listarLivros() {
            System.out.println("Lista de livros:");
            if (livros.isEmpty()) {
            System.out.println("A lista de livros não possui nenhum livro!");
        } else {
            for (Livro livro : livros) {
                String status = livro.isDisponivel() ? "Disponível" : "Emprestado";
                System.out.println(
                        "Título: " + livro.getTitulo() + " | Autor: " + livro.getAutor() + " | Status: " + status);
            }
        }

    }

}
