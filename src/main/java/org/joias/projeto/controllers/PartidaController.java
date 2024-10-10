package org.joias.projeto.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class PartidaController {
    @FXML
    private Button botaoVoltar;
    @FXML
    private Button botaoSair;

    @FXML
    private void onSair() {
        // Fecha a aplicação
        Platform.exit();
    }

    @FXML
    private void onPerguntas() {
        try {
            // Carrega a tela perguntas.fxml
            Parent root = FXMLLoader.load(getClass().getResource("/org/joias/projeto/pergunta.fxml"));
            Stage stage = (Stage) botaoVoltar.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar a tela de perguntas. Verifique o caminho do FXML.");
        }
    }
}
