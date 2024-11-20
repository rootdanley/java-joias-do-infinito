package org.joias.projeto;

import javafx.application.Application;
import javafx.stage.Stage;
import org.joias.projeto.utilidades.SceneManager;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        SceneManager.setStage(primaryStage);
        SceneManager.changeScene("/org/joias/projeto/inicio.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}

