package org.joias.projeto.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class PerguntaController {

    @FXML
    private Label perguntaLabel;
    @FXML
    private Button opcao1, opcao2, opcao3;

    private PartidaController partidaController;
    private String joiaAtual;
    private String respostaCorreta;
    private Scene telaPartidaScene;
    private Map<String, String[]> perguntasMock;

    public void configurarPergunta(PartidaController partidaController, String joiaAtual) {
        this.partidaController = partidaController;
        this.joiaAtual = joiaAtual;
        this.telaPartidaScene = partidaController.getScene(); // Obtém a cena da partida
        inicializarPerguntasMock();
        carregarPergunta();
    }


    private void inicializarPerguntasMock() {
        perguntasMock = new HashMap<>();
        perguntasMock.put("Mente", new String[]{"Qual é a capital da França?", "Paris", "Londres", "Berlim"});
        perguntasMock.put("Espaço", new String[]{"Qual é o maior planeta do sistema solar?", "Júpiter", "Saturno", "Terra"});
        perguntasMock.put("Alma", new String[]{"Quem pintou a Mona Lisa?", "Leonardo da Vinci", "Picasso", "Van Gogh"});
        perguntasMock.put("Poder", new String[]{"Quantos lados tem um hexágono?", "6", "8", "4"});
        perguntasMock.put("Tempo", new String[]{"Quantos segundos tem um minuto?", "60", "100", "120"});
        perguntasMock.put("Realidade", new String[]{"Qual é o elemento químico da água?", "H2O", "O2", "CO2"});
    }

    private void carregarPergunta() {
        String[] perguntaDados = perguntasMock.get(joiaAtual);

        if (perguntaDados != null) {
            perguntaLabel.setText(perguntaDados[0]); // Define a pergunta
            opcao1.setText(perguntaDados[1]); // Primeira opção
            opcao2.setText(perguntaDados[2]); // Segunda opção
            opcao3.setText(perguntaDados[3]); // Terceira opção
            respostaCorreta = perguntaDados[1]; // Resposta correta é sempre a primeira no mock

            // Configura os botões para verificar a resposta
            opcao1.setOnAction(event -> verificarResposta(opcao1.getText()));
            opcao2.setOnAction(event -> verificarResposta(opcao2.getText()));
            opcao3.setOnAction(event -> verificarResposta(opcao3.getText()));
        } else {
            // Caso a pergunta não seja encontrada
            perguntaLabel.setText("Erro: Pergunta não encontrada para a joia " + joiaAtual + ".");
            opcao1.setDisable(true);
            opcao2.setDisable(true);
            opcao3.setDisable(true);
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
