package org.joias.projeto.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioController {
    @FXML
    private Button btnRanking;

    @FXML
    private Button btnJogar;

    @FXML
    private void onRankingButtonClick() throws IOException {
        Parent selecaoRanking = FXMLLoader.load(getClass().getResource("/org/joias/projeto/ranking.fxml"));

        Stage window = (Stage) btnRanking.getScene().getWindow();

        Scene selecaoRankingScene = new Scene(selecaoRanking);
        window.setScene(selecaoRankingScene);
    }

    @FXML
    private void onJogarButtonClick() throws IOException {
        Parent selecaoJogar = FXMLLoader.load(getClass().getResource("/org/joias/projeto/selecaoJogadores.fxml"));

        Stage window = (Stage) btnJogar.getScene().getWindow();
        Scene selecaoJogarScene = new Scene(selecaoJogar);
        window.setScene(selecaoJogarScene);
    }
}
