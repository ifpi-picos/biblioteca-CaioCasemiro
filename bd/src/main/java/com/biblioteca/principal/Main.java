package com.biblioteca.principal;

import com.biblioteca.dao.*;
import com.biblioteca.banco.*;
import com.biblioteca.entidades.*;
import com.biblioteca.servico.*;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try(Connection conexao = Conexaobd.conexao();) {
            UsuarioDAO usuarioDAO = new UsuarioDAO(conexao);
            Biblioteca biblioteca = new Biblioteca(new NotificacaoEmail());

            Scanner scanner = new Scanner(System.in);
            Usuario usuarioLogado = null;

            while (usuarioLogado == null) {
                System.out.println("\n=== SISTEMA DE GERENCIAMENTO DE BIBLIOTECA ===");
                System.out.println("1. Cadastrar usuário");
                System.out.println("2. Login");
                System.out.println("3. Sair");
                System.out.print("Escolha uma opção: ");
                
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao){
                    case 1:
                        System.out.println("Digite o nome de usuário: ");
                        String nome = scanner.nextLine();
                        System.out.println("Crie a senha: ");
                        String senha = scanner.nextLine();
                        biblioteca.cadastrarUsuario(nome, senha);
                        break;
                    case 2: 
                        System.out.println("Digite o nome do usuario: ");
                        String loginNome = scanner.nextLine();
                        System.out.println("Digite a senha: ");
                        String loginSenha = scanner.nextLine();
                        if (biblioteca.login(loginNome, loginSenha)) {
                            usuarioLogado = usuarioDAO.buscarUsuario(loginNome, loginSenha);
                        } else {
                            System.out.println("Tente novamente! Nome ou senha incorretos.");
                        }
                        break;
                    case 3:
                        System.out.println("Saindo do sistema...");
                        scanner.close();
                        return;
                    default:
                    System.out.println("Opção inválida!");
                }
            }

            int opcao;
            do{
                System.out.println("\n=== MENU PRINCIPAL ===");
                System.out.println("1. Listar livros");
                System.out.println("2. Adicionar livro");
                System.out.println("3. Realizar empréstimo");
                System.out.println("4. Devolver livro");
                System.out.println("5. Sair");
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        biblioteca.listarLivros();
                        break;
                    case 2:
                        if(!usuarioLogado.isAdmin()){
                            System.out.println("Apenas administradores podem adicionar livros");
                            break;
                        }
                        System.out.println("Digite o título do livro: ");
                        String titulo = scanner.nextLine();
                        System.out.println("Digite o autor do livro: ");
                        String autor = scanner.nextLine();
                        biblioteca.adicionarLivro(usuarioLogado, titulo, autor);
                        break;
                    case 3:
                        System.out.println("Digite o ID do livro para emprestimo:");
                        int idLivro = scanner.nextInt();
                        scanner.nextLine();
                        biblioteca.realizarEmprestimo(usuarioLogado.getId(), idLivro);
                        break;
                    case 4:
                        System.out.println("Digite o id do emprestimo para devolução: ");
                        int idEmpdevolver = scanner.nextInt();
                        scanner.nextLine();
                        biblioteca.devolverLivros(idEmpdevolver);
                        break;
                    case 5:
                        System.out.println("Saindo do sistema...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } while (opcao != 5);

            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao conectar com o banco de dados");
        }
    }
}
