package org.joias.projeto.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PartidaController {

    @FXML
    private Label nomeJogador1, nomeJogador2, contadorJoiasJogador1, contadorJoiasJogador2, vezJogador1, vezJogador2;
    @FXML
    private ImageView imagemJogador1, imagemJogador2;
    @FXML
    private Label joiaMente, joiaEspaco, joiaAlma, joiaPoder, joiaTempo, joiaRealidade;

    private String personagemJogador1;
    private String personagemJogador2;
    private int jogadorAtual; // 1 para Jogador 1, 2 para Jogador 2
    private int joiasJogador1 = 0;
    private int joiasJogador2 = 0;
    private String joiaSelecionada; // Armazena a joia que está sendo disputada
    private Scene telaPartidaScene;

    public void initialize() {
        if (contadorJoiasJogador1 != null && contadorJoiasJogador2 != null) {
            contadorJoiasJogador1.setText("0/6");
            contadorJoiasJogador2.setText("0/6");
        }
        exibirModalInicio();
    }

    public void setPersonagensJogadores(String personagemJogador1, String personagemJogador2) {
        this.personagemJogador1 = personagemJogador1;
        this.personagemJogador2 = personagemJogador2;

        nomeJogador1.setText(personagemJogador1);
        nomeJogador2.setText(personagemJogador2);

        imagemJogador1.setImage(carregarImagemPersonagem(personagemJogador1));
        imagemJogador2.setImage(carregarImagemPersonagem(personagemJogador2));
    }

    public void setTelaPartidaScene(Scene telaPartidaScene) {
        this.telaPartidaScene = telaPartidaScene;
    }

    private Image carregarImagemPersonagem(String personagem) {
        String caminhoImagem = "/" + personagem.toLowerCase().replace(" ", "_") + ".png";
        var resource = getClass().getResource(caminhoImagem);
        return (resource != null) ? new Image(resource.toExternalForm()) : null;
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
    private void selecionarJoia() {
        Label joiaLabel = (Label) joiaMente.getScene().getFocusOwner();
        if (joiaLabel != null) {
            this.joiaSelecionada = joiaLabel.getText();
            iniciarPergunta();
        }
    }

    private void iniciarPergunta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/joias/projeto/pergunta.fxml"));
            Parent root = loader.load();

            PerguntaController perguntaController = loader.getController();
            perguntaController.configurarPergunta(this, joiaSelecionada, telaPartidaScene);

            Stage stage = (Stage) joiaMente.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ganharJoia() {
        if (jogadorAtual == 1) {
            contadorJoiasJogador1.setText(++joiasJogador1 + "/6");
            verificarVitoria(joiasJogador1, nomeJogador1.getText());
        } else {
            contadorJoiasJogador2.setText(++joiasJogador2 + "/6");
            verificarVitoria(joiasJogador2, nomeJogador2.getText());
        }
        jogadorAtual = (jogadorAtual == 1) ? 2 : 1;
        atualizarVezJogador();
    }

    private void verificarVitoria(int joias, String nomeJogador) {
        if (joias == 6) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setHeaderText("Parabéns!");
            alert.setContentText(nomeJogador + " venceu o jogo!");
            alert.showAndWait();
            Platform.exit();
        }
    }
}
