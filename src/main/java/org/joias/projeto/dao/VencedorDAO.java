package org.joias.projeto.dao;

import org.joias.projeto.models.Vencedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VencedorDAO {
    private Connection conn;

    public VencedorDAO(Connection conn) {
        this.conn = conn;
    }

    public void salvarVencedor(Vencedor vencedor) throws SQLException {
        String sql = "INSERT INTO vencedores (nome_jogador) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, vencedor.getNomeJogador());
        stmt.executeUpdate();
    }

    public List<Vencedor> buscarTodosVencedores() throws SQLException {
        List<Vencedor> vencedores = new ArrayList<>();
        String sql = "SELECT * FROM vencedores";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Vencedor vencedor = new Vencedor(rs.getString("nome"));
            vencedor.setId(rs.getInt("id"));
            vencedores.add(vencedor);
        }

        return vencedores;
    }
}
