package principal;

import dominio.*;
import service.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();

        int opcao;



        do{
            System.out.println("\n=== SISTEMA DE GERENCIAMENTO DE BIBLIOTECA===");
            System.out.println("1. Listar livro");
            System.out.println("2. Adicionar livro");
            System.out.println("3. Adicionar usuário");
            System.out.println("4. Realizar emprestimo");
            System.out.println("5. Sair");
            System.out.println("Escolha uma opção:");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    biblioteca.listarLivros();
                    break;
            
                case 2:
                System.out.println("Digite o título do livro: ");
                String titulo = scanner.nextLine();
                System.out.println("Digite o autor:");
                String autor = scanner.nextLine();
                System.out.println("Digite o gênero do livro: ");
                String genero = scanner.nextLine();

                Livro livro = new Livro(titulo, autor, genero, true);
                biblioteca.adicionarLivro(livro);
                break;

                case 3:
                System.out.println("Digite o nome do usuário: ");
                String nome = scanner.nextLine();

                Usuario usuario = new Usuario(nome);
                biblioteca.adicionarUsuario(usuario);
                break;

                case 4:
                System.out.println("Digite o ID do usuário:");
                int idUsuario = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Digite o título do livro: ");
                String tituloLivro = scanner.nextLine();

                biblioteca.realizarEmprestimo(idUsuario, tituloLivro);
                break;

                case 5:
                System.out.println("Saindo do sistema!");
                break;


                default:
                    break;
            }
            
        } while(opcao != 5);
            scanner.close();
        
    }
}
