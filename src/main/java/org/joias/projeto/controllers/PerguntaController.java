package org.joias.projeto.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PerguntaController {

    @FXML
    private Label perguntaLabel;
    @FXML
    private Button opcao1, opcao2, opcao3;

    private PartidaController partidaController;
    private Scene telaPartidaScene;
    private String respostaCorreta;

    public void configurarPergunta(PartidaController partidaController, String joiaAtual, Scene telaPartidaScene) {
        this.partidaController = partidaController;
        this.telaPartidaScene = telaPartidaScene;
        carregarPergunta(joiaAtual);
    }

    private void carregarPergunta(String joiaAtual) {
        perguntaLabel.setText("Qual é a resposta correta para ganhar a joia " + joiaAtual + "?");
        respostaCorreta = "Opção 1";

        opcao1.setText("Opção 1");
        opcao2.setText("Opção 2");
        opcao3.setText("Opção 3");

        opcao1.setOnAction(event -> verificarResposta(opcao1.getText()));
        opcao2.setOnAction(event -> verificarResposta(opcao2.getText()));
        opcao3.setOnAction(event -> verificarResposta(opcao3.getText()));
    }

    private void verificarResposta(String respostaEscolhida) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (respostaEscolhida.equals(respostaCorreta)) {
            alert.setHeaderText("Resposta Correta!");
            alert.setContentText("Você ganhou a joia!");
            partidaController.ganharJoia();
        } else {
            alert.setHeaderText("Resposta Incorreta!");
            alert.setContentText("Você não ganhou a joia.");
        }
        alert.showAndWait();
        voltarParaPartida();
    }

    private void voltarParaPartida() {
        Stage stage = (Stage) perguntaLabel.getScene().getWindow();
        stage.setScene(telaPartidaScene);
        partidaController.atualizarVezJogador();
    }
}
