package org.joias.projeto.dao;

import org.joias.projeto.models.Vencedor;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TesteAdicionarVencedor {

    public static void main(String[] args) {
        adicionarVencedor("Jogador Teste");
    }

    public static void adicionarVencedor(String nomeJogador) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            Vencedor vencedor = new Vencedor(nomeJogador);
            VencedorDAO vencedorDAO = new VencedorDAO(conn);
            vencedorDAO.salvarVencedor(vencedor);

            System.out.println("Vencedor '" + nomeJogador + "' adicionado com sucesso ao banco de dados!");
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar vencedor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
