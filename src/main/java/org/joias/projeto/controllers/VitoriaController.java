package org.joias.projeto.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.joias.projeto.dao.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VitoriaController {

    @FXML
    private TextField nomeJogadorField;
    @FXML
    private Button salvarButton;

    private String nomeJogador;

    public void setNomeJogador(String nomeJogador) {
        this.nomeJogador = nomeJogador;
        nomeJogadorField.setText(nomeJogador); // Preenche o campo com o nome do jogador
    }

    @FXML
    private void salvarVencedor() {
        String nome = nomeJogadorField.getText();

        if (nome == null || nome.trim().isEmpty()) {
            exibirMensagem("Erro", "O nome do jogador não pode estar vazio.");
            return;
        }

        String query = "INSERT INTO vencedores (nome) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nome);
            stmt.executeUpdate();

            exibirMensagem("Sucesso", "Vencedor salvo com sucesso!");

            // Fecha o modal
            Stage stage = (Stage) salvarButton.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            exibirMensagem("Erro", "Não foi possível salvar o vencedor. Verifique a conexão com o banco de dados.");
            e.printStackTrace();
        }
    }

    private void exibirMensagem(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
