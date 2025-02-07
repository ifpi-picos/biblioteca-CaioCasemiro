package com.biblioteca.dao;

import com.biblioteca.banco.Conexaobd;
import com.biblioteca.entidades.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class EmprestimoDAO {
    private Connection conexao;

    public EmprestimoDAO(Connection conexao){
        this.conexao = conexao;
    }

    public int cadastrarEmprestimo(Emprestimo emprestimo){
        String sql = "INSERT INTO emprestimos (idusuario, idlivro, dataemprestimo, datadevolucao) VALUES (?, ?, ?, ?)";

        try(Connection conexao = Conexaobd.conexao();
        PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, emprestimo.getUsuario().getId());
            stmt.setInt(2, emprestimo.getLivro().getId());
            stmt.setDate(3, Date.valueOf(emprestimo.getDataEmprestimo()));
            stmt.setDate(4, Date.valueOf(emprestimo.getDataDevolucao()));
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if(generatedKeys.next()){
                int idEmprestimoGerado = generatedKeys.getInt(1);
                System.out.println("Emprestimo cadastrado");
                return idEmprestimoGerado;
            }else{
                throw new SQLException("Falha ao receber id!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void finalizarEmprestimo(int idEmprestimo){
        String sql = "DELETE FROM emprestimos WHERE idemprestimo = ?";

        try(Connection conexao = Conexaobd.conexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idEmprestimo);
            int linhasAfetadas = stmt.executeUpdate();

            if(linhasAfetadas>0) {
                System.out.println("Emprestimo finalizado com sucesso");
            } else {
                System.out.println("Emprestimo não localizado");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Emprestimo> listarEmprestimos(){
        List<Emprestimo> emprestimos = new ArrayList<>();
        String sql = "SELECT * FROM emprestimos";

        try(Connection conexao = Conexaobd.conexao();
        Statement stmt = conexao.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int id = rs.getInt("idemprestimo");
                int idUsuario = rs.getInt("idusuario");
                int idLivro = rs.getInt("idlivro");
                LocalDate dataEmprestimo = rs.getDate("dataemprestimo").toLocalDate();
                LocalDate dataDevolucao = rs.getDate("datadevolucao").toLocalDate();
                
                Usuario usuario = new UsuarioDAO(conexao).buscarUsuarioPorId(idUsuario);
                Livro livro = new LivroDAO(conexao).buscarLivroPorId(idLivro);

                emprestimos.add(new Emprestimo(id, usuario, livro, dataEmprestimo, dataDevolucao)); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprestimos;
    }

    public Emprestimo buscarEmprestimoPorId(int idEmprestimo){
        String sql = "SELECT * FROM emprestimos WHERE idemprestimo = ?";
        Emprestimo emprestimo = null;

        try(Connection conexao = Conexaobd.conexao();
        PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, idEmprestimo);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                int idUsuario = rs.getInt("idusuario");
                int idLivro = rs.getInt("idlivro");
                LocalDate dataEmprestimo = rs.getDate("dataemprestimo").toLocalDate();
                LocalDate dataDevolucao = rs.getDate("datadevolucao").toLocalDate();

                Usuario usuario = new UsuarioDAO(conexao).buscarUsuarioPorId(idUsuario);
                Livro livro = new LivroDAO(conexao).buscarLivroPorId(idLivro);

                emprestimo = new Emprestimo(idEmprestimo, usuario, livro, dataEmprestimo, dataDevolucao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emprestimo;
    }
}
