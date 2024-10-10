package org.joias.projeto.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.joias.projeto.utilidades.SceneManager;


import java.io.IOException;

public class PerguntasController {

    @FXML
    private Button sairButton;

    @FXML
    private void SairButton() throws IOException {
        SceneManager.changeScene("/org/joias/projeto/inicio.fxml");
    }

    @FXML
    private void handleSairButtonAction() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/joias/projeto/inicio.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) sairButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
