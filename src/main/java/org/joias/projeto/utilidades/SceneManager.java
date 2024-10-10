package org.joias.projeto.utilidades;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    private static Stage stage;


    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void changeScene(String fxmlFile) {
        try {
            Parent root = FXMLLoader.load(SceneManager.class.getResource(fxmlFile));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
