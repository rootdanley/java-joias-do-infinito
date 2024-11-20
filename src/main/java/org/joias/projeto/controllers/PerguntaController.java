package org.joias.projeto.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.joias.projeto.dao.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PerguntaController {

    @FXML
    private Label perguntaLabel;
    @FXML
    private Button opcao1, opcao2, opcao3;

    private PartidaController partidaController;
    private String joiaAtual;
    private String respostaCorreta;
    private Scene telaPartidaScene;

    public void configurarPergunta(PartidaController partidaController, String joiaAtual, Scene telaPartidaScene) {
        this.partidaController = partidaController;
        this.joiaAtual = joiaAtual.toLowerCase(); // Certifica-se de que a joia está em minúsculo
        this.telaPartidaScene = telaPartidaScene; // Armazena a cena da partida
        carregarPergunta();
    }

    private void carregarPergunta() {
        String query = """
                SELECT id, pergunta, opcao_correta, opcao2, opcao3 
                FROM perguntas 
                WHERE joia = ? AND respondida = FALSE 
                ORDER BY RAND() 
                LIMIT 1
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, joiaAtual);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int perguntaId = rs.getInt("id");
                perguntaLabel.setText(rs.getString("pergunta"));
                opcao1.setText(rs.getString("opcao_correta"));
                opcao2.setText(rs.getString("opcao2"));
                opcao3.setText(rs.getString("opcao3"));
                respostaCorreta = rs.getString("opcao_correta");

                // Atualiza a pergunta como respondida
                marcarPerguntaComoRespondida(perguntaId);

                // Configura os botões para verificar a resposta
                opcao1.setOnAction(event -> verificarResposta(opcao1.getText()));
                opcao2.setOnAction(event -> verificarResposta(opcao2.getText()));
                opcao3.setOnAction(event -> verificarResposta(opcao3.getText()));
            } else {
                // Caso nenhuma pergunta esteja disponível
                perguntaLabel.setText("Sem mais perguntas disponíveis para a joia " + joiaAtual + ".");
                opcao1.setDisable(true);
                opcao2.setDisable(true);
                opcao3.setDisable(true);
            }

        } catch (SQLException e) {
            exibirMensagem("Erro", "Não foi possível carregar a pergunta. Verifique a conexão com o banco de dados.");
            e.printStackTrace();
        }
    }

    private void marcarPerguntaComoRespondida(int perguntaId) {
        String query = "UPDATE perguntas SET respondida = TRUE WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, perguntaId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao marcar pergunta como respondida: " + e.getMessage());
        }
    }

    private void verificarResposta(String respostaEscolhida) {
        boolean acertou = respostaEscolhida.equals(respostaCorreta);

        if (acertou) {
            exibirMensagem("Resposta Correta!", "Você ganhou a joia " + joiaAtual + "!");
        } else {
            exibirMensagem("Resposta Incorreta!", "Você não ganhou a joia. Passando a vez.");
        }

        // Atualiza o estado no PartidaController e retorna à tela de partida
        partidaController.ganharJoia(acertou);
        voltarParaPartida();
    }

    private void exibirMensagem(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void voltarParaPartida() {
        Stage stage = (Stage) perguntaLabel.getScene().getWindow();
        stage.setScene(telaPartidaScene); // Volta para a tela de partida
        partidaController.atualizarVezJogador(); // Atualiza o estado da partida
    }
}
