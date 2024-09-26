package org.joias.projeto.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.joias.projeto.utils.SceneManager;

import java.io.IOException;

public class SelecaoJogadoresController {

    @FXML
    private Button btnVoltar;






    @FXML
    private void onVoltarButtonClick() throws IOException {
        SceneManager.changeScene("/org/joias/projeto/inicio.fxml");
    }
}
