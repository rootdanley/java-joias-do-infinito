package org.joias.projeto.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PartidaController {

    @FXML
    private Label nomeJogador1, nomeJogador2, contadorJoiasJogador1, contadorJoiasJogador2, vezJogador1, vezJogador2;
    @FXML
    private HBox inventarioJogador1, inventarioJogador2; // Inventários visuais
    @FXML
    private Label joiaMente, joiaEspaco, joiaAlma, joiaPoder, joiaTempo, joiaRealidade;

    private String personagemJogador1;
    private String personagemJogador2;
    private int jogadorAtual; // 1 para Jogador 1, 2 para Jogador 2
    private List<String> inventario1 = new ArrayList<>(); // Inventário do Jogador 1
    private List<String> inventario2 = new ArrayList<>(); // Inventário do Jogador 2
    private static final int TOTAL_JOIAS = 6; // Total de joias necessárias para vencer
    private String joiaSelecionada; // Armazena a joia que está sendo disputada

    public void initialize() {
        contadorJoiasJogador1.setText("0/" + TOTAL_JOIAS);
        contadorJoiasJogador2.setText("0/" + TOTAL_JOIAS);
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
        alert.setTitle("Bem-vindo");
        alert.setHeaderText(null);
        alert.setContentText("A partida começará em breve!");
        alert.showAndWait();
        escolherJogadorInicial();
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
        Label joiaLabel = (Label) event.getSource(); // Identifica a joia selecionada
        if (joiaLabel != null) {
            joiaSelecionada = padronizarNomeJoia(joiaLabel.getText());

            // Verifica se a joia já está no inventário do jogador atual
            if (jogadorAtual == 1 && inventario1.contains(joiaSelecionada)) {
                exibirAlerta("Joia já conquistada", "Você já possui esta joia. Escolha outra.");
                return;
            } else if (jogadorAtual == 2 && inventario2.contains(joiaSelecionada)) {
                exibirAlerta("Joia já conquistada", "Você já possui esta joia. Escolha outra.");
                return;
            }

            // Inicia a tela de perguntas
            iniciarPergunta();
        }
    }


    private String padronizarNomeJoia(String joia) {
        return joia.toLowerCase() // Deixa todas as letras em minúsculo
                .replace("joia da ", "") // Remove "joia da"
                .replace("joia do ", "") // Remove "joia do"
                .trim(); // Remove espaços extras
    }

    private void iniciarPergunta() {
        try {
            // Padroniza o nome da joia para correspondência
            joiaSelecionada = joiaSelecionada.trim().toLowerCase(); // Padroniza o nome para minúsculas

            System.out.println("Iniciando pergunta para a joia: " + joiaSelecionada); // Log para depuração

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/joias/projeto/pergunta.fxml"));
            Parent root = loader.load();

            PerguntaController perguntaController = loader.getController();
            perguntaController.configurarPergunta(this, joiaSelecionada, nomeJogador1.getScene());

            Stage stage = (Stage) joiaMente.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void ganharJoia(boolean respostaCorreta) {
        if (respostaCorreta) {
            if (jogadorAtual == 1) { // Jogador 1 acertou a pergunta
                // Transfere a joia do adversário (Jogador 2), se necessário
                if (inventario2.contains(joiaSelecionada)) {
                    inventario2.remove(joiaSelecionada); // Remove do inventário do Jogador 2
                    contadorJoiasJogador2.setText(inventario2.size() + "/" + TOTAL_JOIAS); // Atualiza o contador do Jogador 2
                    atualizarInventarioVisual(inventarioJogador2, inventario2); // Atualiza o inventário visual do Jogador 2
                }
                inventario1.add(joiaSelecionada); // Adiciona ao inventário do Jogador 1
                contadorJoiasJogador1.setText(inventario1.size() + "/" + TOTAL_JOIAS); // Atualiza o contador do Jogador 1
                atualizarInventarioVisual(inventarioJogador1, inventario1); // Atualiza o inventário visual do Jogador 1
            } else { // Jogador 2 acertou a pergunta
                // Transfere a joia do adversário (Jogador 1), se necessário
                if (inventario1.contains(joiaSelecionada)) {
                    inventario1.remove(joiaSelecionada); // Remove do inventário do Jogador 1
                    contadorJoiasJogador1.setText(inventario1.size() + "/" + TOTAL_JOIAS); // Atualiza o contador do Jogador 1
                    atualizarInventarioVisual(inventarioJogador1, inventario1); // Atualiza o inventário visual do Jogador 1
                }
                inventario2.add(joiaSelecionada); // Adiciona ao inventário do Jogador 2
                contadorJoiasJogador2.setText(inventario2.size() + "/" + TOTAL_JOIAS); // Atualiza o contador do Jogador 2
                atualizarInventarioVisual(inventarioJogador2, inventario2); // Atualiza o inventário visual do Jogador 2
            }
            verificarVitoria(); // Verifica se algum jogador venceu
        } else {
            // Passa a vez para o próximo jogador
            jogadorAtual = (jogadorAtual == 1) ? 2 : 1;
            atualizarVezJogador(); // Atualiza a vez do jogador
        }
    }


    private void atualizarInventarioVisual(HBox inventarioVisual, List<String> inventario) {
        inventarioVisual.getChildren().clear();
        for (String joia : inventario) {
            Text novaJoia = new Text(joia);
            novaJoia.setStyle("-fx-font-size: 14px; -fx-padding: 5px; -fx-border-color: black; -fx-border-width: 1px;");
            inventarioVisual.getChildren().add(novaJoia);
        }
    }

    private void verificarVitoria() {
        if (inventario1.size() == TOTAL_JOIAS) {
            exibirAlerta("Vitória!", nomeJogador1.getText() + " venceu o jogo!");
            Platform.exit();
        } else if (inventario2.size() == TOTAL_JOIAS) {
            exibirAlerta("Vitória!", nomeJogador2.getText() + " venceu o jogo!");
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
