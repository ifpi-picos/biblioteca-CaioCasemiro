package com.biblioteca.banco;

import java.sql.*;

public class Conexaobd {
    private static final String url = "jdbc:postgresql://localhost:5432/teste";
    private static final String user = "postgres";
    private static final String senha = "caio2706";

    public static Connection conexao(){
        try {
            return DriverManager.getConnection(url, user, senha);
        } catch (SQLException e) {
            System.out.println("Erro de conexão!");
            e.printStackTrace();
            return null;
        }
    }
}
