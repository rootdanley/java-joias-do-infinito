package org.joias.projeto.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class PartidaController {

    @FXML
    private Label nomeJogador1, nomeJogador2, contadorJoiasJogador1, contadorJoiasJogador2, vezJogador1, vezJogador2;
    @FXML
    private HBox inventarioJogador1, inventarioJogador2; // Inventários visíveis
    @FXML
    private Label joiaMente, joiaEspaco, joiaAlma, joiaPoder, joiaTempo, joiaRealidade;

    private String personagemJogador1;
    private String personagemJogador2;
    private int jogadorAtual; // 1 para Jogador 1, 2 para Jogador 2
    private int joiasJogador1 = 0;
    private int joiasJogador2 = 0;
    private String joiaSelecionada; // Armazena a joia que está sendo disputada
    private Set<String> inventario1 = new HashSet<>(); // Joias do Jogador 1
    private Set<String> inventario2 = new HashSet<>(); // Joias do Jogador 2

    public void initialize() {
        contadorJoiasJogador1.setText("0/6");
        contadorJoiasJogador2.setText("0/6");
        exibirModalInicio();
    }

    public void setPersonagensJogadores(String personagemJogador1, String personagemJogador2) {
        this.personagemJogador1 = personagemJogador1;
        this.personagemJogador2 = personagemJogador2;

        nomeJogador1.setText(personagemJogador1);
        nomeJogador2.setText(personagemJogador2);
    }

    private void exibirModalInicio() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setHeaderText(null);
        alert.setContentText("A partida começará em breve...");
        alert.show();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    alert.close();
                    escolherJogadorInicial();
                });
                timer.cancel();
            }
        }, 2000);
    }

    private void escolherJogadorInicial() {
        jogadorAtual = new Random().nextInt(2) + 1;
        atualizarVezJogador();
    }

    public void atualizarVezJogador() {
        vezJogador1.setText(jogadorAtual == 1 ? "É sua vez!" : "");
        vezJogador2.setText(jogadorAtual == 2 ? "É sua vez!" : "");
    }

    @FXML
    private void selecionarJoia(javafx.scene.input.MouseEvent event) {
        Label joiaLabel = (Label) event.getSource(); // Identifica o Label clicado
        if (joiaLabel != null) {
            joiaSelecionada = joiaLabel.getText().trim();

            // Padroniza o nome da joia para corresponder às chaves do mock
            if (joiaSelecionada.startsWith("Joia da")) {
                joiaSelecionada = joiaSelecionada.replace("Joia da ", "").trim();
            } else if (joiaSelecionada.startsWith("Joia do")) {
                joiaSelecionada = joiaSelecionada.replace("Joia do ", "").trim();
            }

            // Inicia a pergunta
            iniciarPergunta();
        }
    }


    private void iniciarPergunta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/joias/projeto/pergunta.fxml"));
            Parent root = loader.load();

            PerguntaController perguntaController = loader.getController();
            perguntaController.configurarPergunta(this, joiaSelecionada);

            Stage stage = (Stage) joiaMente.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ganharJoia(boolean respostaCorreta) {
        if (respostaCorreta) {
            if (jogadorAtual == 1) {
                inventario1.add(joiaSelecionada);
                contadorJoiasJogador1.setText(++joiasJogador1 + "/6");
                atualizarInventario(inventarioJogador1, joiaSelecionada);
            } else {
                inventario2.add(joiaSelecionada);
                contadorJoiasJogador2.setText(++joiasJogador2 + "/6");
                atualizarInventario(inventarioJogador2, joiaSelecionada);
            }
            verificarVitoria(); // Verifica se o jogador venceu
        } else {
            // Passa a vez para o próximo jogador
            jogadorAtual = (jogadorAtual == 1) ? 2 : 1;
            atualizarVezJogador();
        }
    }
    public Scene getScene() {
        // Retorna a cena atual da janela onde o controlador está ativo
        return nomeJogador1.getScene();
    }


    private void atualizarInventario(HBox inventario, String joia) {
        Text novaJoia = new Text(joia);
        novaJoia.setStyle("-fx-font-size: 14px; -fx-padding: 5px; -fx-border-color: black; -fx-border-width: 1px;");
        inventario.getChildren().add(novaJoia); // Adiciona a joia ao inventário visual
    }

    private void verificarVitoria() {
        if (joiasJogador1 == 6) {
            exibirAlerta("Parabéns!", nomeJogador1.getText() + " venceu o jogo!");
            Platform.exit();
        } else if (joiasJogador2 == 6) {
            exibirAlerta("Parabéns!", nomeJogador2.getText() + " venceu o jogo!");
            Platform.exit();
        }
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
