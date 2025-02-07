package com.biblioteca.dao;

import com.biblioteca.entidades.Livro;
import com.biblioteca.banco.Conexaobd;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {
    private Connection conexao;

    public LivroDAO(Connection conexao){
        this.conexao = conexao;
    }

    public void inserirLivro(Livro livro) {
        String sql = "INSERT INTO livro (titulolivro, autorlivro, isdisponivel) VALUES (?, ?, ?)";

        try (Connection conexao = Conexaobd.conexao();
                PreparedStatement stmt = conexao.prepareStatement(sql);) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setBoolean(3, livro.isDisponivel());

            stmt.executeUpdate();
            System.out.println("Livro adicionado com sucesso");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Livro> listarLivros() {
        List<Livro> livros = new ArrayList<>();
        String sql = "SELECT * FROM livro";

        try (Connection conexao = Conexaobd.conexao();
                Statement stmt = conexao.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Livro livro = new Livro(rs.getInt("idlivro"), rs.getString("titulolivro"), rs.getString("autorlivro"), rs.getBoolean("isdisponivel"));
                livros.add(livro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livros;
    }

    public void atualizarDisponibilidade(int idLivro, Boolean disponivel) {
        String sql = "UPDATE livro SET isdisponivel = ? WHERE idlivro = ?";
        try (Connection conexao = Conexaobd.conexao();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setBoolean(1, disponivel);
            stmt.setInt(2, idLivro);
            stmt.executeUpdate();

            System.out.println("Disponibilidade do livro atualizada");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarLivro(String titulo) {
        String sql = "DELETE FROM livro WHERE titulolivro = ?";
        try (Connection conexao = Conexaobd.conexao();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, titulo);
            stmt.executeUpdate();

            System.out.println("Livro deletado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Livro buscarLivroPorId(int idLivro) {
        String sql = "SELECT * FROM livro WHERE idlivro = ?";
        Livro livro = null;

        try (Connection conexao = Conexaobd.conexao();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idLivro);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Boolean disponivel = rs.getBoolean("isdisponivel");
                livro = new Livro(rs.getInt("idlivro"), rs.getString("titulolivro"), rs.getString("autorlivro"), disponivel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return livro;
    }

}
