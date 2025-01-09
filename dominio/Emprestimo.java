package dominio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Emprestimo {
    private int idEmprestimo;
    private Usuario usuario;
    private Livro livro;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;

    public Emprestimo(int idEmprestimo, Usuario usuario, Livro livro) {
        this.idEmprestimo = idEmprestimo;
        this.usuario = usuario;
        this.livro = livro;
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucao = dataEmprestimo.plusDays(14);
        this.livro.setDisponivel(false);
    }

    public void finalizarEmprestimo() {
        livro.setDisponivel(true);
        System.out.println("Empréstimo finalizado. Livro devolvido com sucesso!");
    }

    public boolean estaAtrasado() {
        return LocalDate.now().isAfter(dataDevolucao);
    }

    public double calcularMulta() {
        if (estaAtrasado()) {
            long diasAtraso = ChronoUnit.DAYS.between(dataDevolucao, LocalDate.now());
            return diasAtraso * 2.0;
        }
        return 0.0;
    }

    public int getIdEmprestimo() {
        return idEmprestimo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }
}
