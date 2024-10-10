package org.joias.projeto.dao;

import org.joias.projeto.models.Vencedor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TesteVencedores {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            VencedorDAO vencedorDAO = new VencedorDAO(conn);
            List<Vencedor> vencedores = vencedorDAO.buscarTodosVencedores();

            // Exibindo os vencedores recuperados do banco de dados
            for (Vencedor vencedor : vencedores) {
                System.out.println(vencedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
