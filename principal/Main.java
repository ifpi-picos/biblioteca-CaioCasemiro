package principal;

import dominio.*;
import servico.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Notificacao notificacao = new NotificacaoEmail();
        Biblioteca biblioteca = new Biblioteca(notificacao);
        int opcao;

        do {
            System.out.println("\n=== SISTEMA DE GERENCIAMENTO DE BIBLIOTECA ===");
            if (!biblioteca.isUsuarioLogado()) {    
                System.out.println("1. Cadastrar usuário");
                System.out.println("2. Login");
                System.out.println("3. Sair");
            } else {
                System.out.println("1. Listar livros");
                System.out.println("2. Adicionar livro");
                System.out.println("3. Realizar empréstimo");
                System.out.println("4. Devolver livro");
                System.out.println("5. Listar histórico de usuário");
                System.out.println("6. Logout");
                System.out.println("7. Sair");
            }

            System.out.println("Escolha uma opção:");
            opcao = scanner.nextInt();
            scanner.nextLine();

            if (!biblioteca.isUsuarioLogado()) {
                switch (opcao) {
                    case 1:
                        System.out.println("Digite o nome de usuário: ");
                        String nome = scanner.nextLine();
                        System.out.println("Digite a senha: ");
                        String senha = scanner.nextLine();
                        biblioteca.cadastrarUsuario(nome, senha);
                        break;

                    case 2:
                        System.out.println("Digite o nome de usuário: ");
                        String loginNome = scanner.nextLine();
                        System.out.println("Digite a senha: ");
                        String loginSenha = scanner.nextLine();
                        biblioteca.login(loginNome, loginSenha);
                        break;

                    case 3:
                        System.out.println("Saindo do sistema!");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                        break;
                }
            } else {
                switch (opcao) {
                    case 1:
                        biblioteca.listarLivros();
                        break;

                    case 2:
                        System.out.println("Digite o título do livro: ");
                        String titulo = scanner.nextLine();
                        System.out.println("Digite o autor: ");
                        String autor = scanner.nextLine();
                        Livro livro = new Livro(titulo, autor, true);
                        biblioteca.adicionarLivro(livro);
                        break;

                    case 3:
                        System.out.println("Digite o título do livro: ");
                        String tituloLivro = scanner.nextLine();
                        biblioteca.realizarEmprestimo(biblioteca.getUsuarioLogado().getId(), tituloLivro);
                        break;

                    case 4:
                        System.out.println("Digite o título do livro para devolução: ");
                        String livroDevolucao = scanner.nextLine();
                        biblioteca.devolverLivro(livroDevolucao);
                        break;

                    case 5:
                        biblioteca.listarHistoricoUsuario();
                        break;

                    case 6:
                        biblioteca.logout();
                        break;

                    case 7:
                        System.out.println("Saindo do sistema!");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                        break;
                }
            }

        } while (opcao != 3 && opcao != 7);

        scanner.close();
    }
}
