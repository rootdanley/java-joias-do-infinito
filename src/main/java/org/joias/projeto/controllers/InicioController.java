package org.joias.projeto.controllers;

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
    private void onRankingButtonClick() throws IOException {
        SceneManager.changeScene("/org/joias/projeto/ranking.fxml");
    }

    @FXML
    private void onJogarButtonClick() throws IOException {
        SceneManager.changeScene("/org/joias/projeto/selecaoPersonagem.fxml");
    }

}
