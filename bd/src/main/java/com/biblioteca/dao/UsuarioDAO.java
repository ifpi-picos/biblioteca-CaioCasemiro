package com.biblioteca.dao;

import com.biblioteca.banco.Conexaobd;
import com.biblioteca.entidades.Usuario;
import java.sql.*;

public class UsuarioDAO {
    private Connection conexao;

    public UsuarioDAO(Connection conexao){
        this.conexao = conexao;
    }

    public void inserirUsuario(String nome, String senha, boolean isadmin) {
        String sql = "INSERT INTO usuarios (nomeusuario, senha, isadmin) VALUES (?, ?, ?)";

        try (Connection conexao = Conexaobd.conexao();
                PreparedStatement stmt = conexao.prepareStatement(sql);) {

            stmt.setString(1, nome);
            stmt.setString(2, senha);
            stmt.setBoolean(3, isadmin);
            stmt.executeUpdate();

            System.out.println("Usuário cadastrado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Usuario buscarUsuario(String nome, String senha) {
        String sql = "SELECT * FROM usuarios WHERE nomeusuario = ? AND senha = ?";

        try (Connection conexao = Conexaobd.conexao();
                PreparedStatement stmt = conexao.prepareStatement(sql);) {

            stmt.setString(1, nome);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(rs.getInt("idusuario"), rs.getString("nomeusuario"), rs.getString("senha"), rs.getBoolean("isadmin"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deletarUsuario(int idUsuario) {
        String sql = "DELETE FROM usuarios WHERE idusuario = ?";

        try (Connection conexao = Conexaobd.conexao();
                PreparedStatement stmt = conexao.prepareStatement(sql);) {

            stmt.setInt(1, idUsuario);
            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario buscarUsuarioPorId(int idUsuario) {
        String sql = "SELECT * FROM usuarios WHERE idusuario = ?";
        Usuario usuario = null;

        try (Connection conexao = Conexaobd.conexao();
                PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            ResultSet resultado = stmt.executeQuery();

            if (resultado.next()) {
                usuario = new Usuario(resultado.getInt("idusuario"), resultado.getString("nomeusuario"),
                        resultado.getString("senha"), null);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }

}
