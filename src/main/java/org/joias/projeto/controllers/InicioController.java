package org.joias.projeto.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.joias.projeto.utilidades.SceneManager;

import java.io.IOException;

public class InicioController {
    @FXML
    private Button btnRanking;

    @FXML
    private Button btnJogar;

    @FXML
    private Button botaoSair;

    @FXML
    private void onSair() {
        // Fecha a aplicação
        Platform.exit();
    }

    @FXML
    private void onRankingButtonClick() throws IOException {
        SceneManager.changeScene("/org/joias/projeto/ranking.fxml");
    }

    @FXML
    private void onJogarButtonClick() throws IOException {
        SceneManager.changeScene("/org/joias/projeto/selecaoPersonagem.fxml");
    }

}
