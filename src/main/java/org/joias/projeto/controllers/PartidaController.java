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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class PartidaController {
    @FXML
    private ImageView imagemMente;
    @FXML
    private ImageView imagemEspaco;
    @FXML
    private ImageView imagemAlma;
    @FXML
    private ImageView imagemPoder;
    @FXML
    private ImageView imagemTempo;
    @FXML
    private ImageView imagemRealidade;

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
    private Map<String, String> mapaJoiasParaImagens; // Mapeamento de joias para caminhos das imagens
    private static final int TOTAL_JOIAS = 6; // Total de joias necessárias para vencer
    private String joiaSelecionada; // Armazena a joia que está sendo disputada

    public void initialize() {
        inicializarMapaDeJoias();
        carregarImagensDasJoias(); // Carrega as imagens para os ImageView das joias
        contadorJoiasJogador1.setText("0/" + TOTAL_JOIAS);
        contadorJoiasJogador2.setText("0/" + TOTAL_JOIAS);
        exibirModalInicio();
    }
    private void carregarImagem(String joia, ImageView imageView) {
        String caminhoImagem = mapaJoiasParaImagens.get(joia);
        if (caminhoImagem != null) {
            imageView.setImage(new Image(getClass().getResource(caminhoImagem).toExternalForm()));
        } else {
            System.out.println("Erro ao carregar imagem para a joia: " + joia);
        }
    }
    private void carregarImagensDasJoias() {
        carregarImagem("mente", imagemMente);
        carregarImagem("espaço", imagemEspaco);
        carregarImagem("alma", imagemAlma);
        carregarImagem("poder", imagemPoder);
        carregarImagem("tempo", imagemTempo);
        carregarImagem("realidade", imagemRealidade);
    }



    public void setPersonagensJogadores(String personagemJogador1, String personagemJogador2) {
        this.personagemJogador1 = personagemJogador1;
        this.personagemJogador2 = personagemJogador2;

        nomeJogador1.setText(personagemJogador1);
        nomeJogador2.setText(personagemJogador2);
    }

    private void inicializarMapaDeJoias() {
        mapaJoiasParaImagens = new HashMap<>();
        mapaJoiasParaImagens.put("mente", "/org/joias/projeto/images/joia_mente.png");
        mapaJoiasParaImagens.put("espaço", "/org/joias/projeto/images/joia_espaco.png");
        mapaJoiasParaImagens.put("alma", "/org/joias/projeto/images/joia_alma.png");
        mapaJoiasParaImagens.put("poder", "/org/joias/projeto/images/joia_poder.png");
        mapaJoiasParaImagens.put("tempo", "/org/joias/projeto/images/joia_tempo.png");
        mapaJoiasParaImagens.put("realidade", "/org/joias/projeto/images/joia_realidade.png");
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
        // Identifica o VBox clicado
        VBox joiaVBox = (VBox) event.getSource();

        // Encontra o Label dentro do VBox que contém o nome da joia
        Label joiaLabel = (Label) joiaVBox.getChildren().stream()
                .filter(node -> node instanceof Label)
                .findFirst()
                .orElse(null);

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
        } else {
            exibirAlerta("Erro", "Não foi possível identificar a joia selecionada.");
        }
    }


    private String padronizarNomeJoia(String joia) {
        return joia.toLowerCase()
                .replace("joia da ", "")
                .replace("joia do ", "")
                .trim();
    }

    private void iniciarPergunta() {
        try {
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
                if (inventario2.contains(joiaSelecionada)) {
                    inventario2.remove(joiaSelecionada);
                    contadorJoiasJogador2.setText(inventario2.size() + "/" + TOTAL_JOIAS);
                    atualizarInventarioVisual(inventarioJogador2, inventario2);
                }
                inventario1.add(joiaSelecionada);
                contadorJoiasJogador1.setText(inventario1.size() + "/" + TOTAL_JOIAS);
                atualizarInventarioVisual(inventarioJogador1, inventario1);
            } else { // Jogador 2 acertou a pergunta
                if (inventario1.contains(joiaSelecionada)) {
                    inventario1.remove(joiaSelecionada);
                    contadorJoiasJogador1.setText(inventario1.size() + "/" + TOTAL_JOIAS);
                    atualizarInventarioVisual(inventarioJogador1, inventario1);
                }
                inventario2.add(joiaSelecionada);
                contadorJoiasJogador2.setText(inventario2.size() + "/" + TOTAL_JOIAS);
                atualizarInventarioVisual(inventarioJogador2, inventario2);
            }
            verificarVitoria();
        } else {
            jogadorAtual = (jogadorAtual == 1) ? 2 : 1;
            atualizarVezJogador();
        }
    }

    private void atualizarInventarioVisual(HBox inventarioVisual, List<String> inventario) {
        inventarioVisual.getChildren().clear();
        for (String joia : inventario) {
            String caminhoImagem = mapaJoiasParaImagens.get(joia);
            if (caminhoImagem != null) {
                ImageView imagemJoia = new ImageView(new Image(getClass().getResource(caminhoImagem).toExternalForm()));
                imagemJoia.setFitHeight(50);
                imagemJoia.setFitWidth(50);
                imagemJoia.setPreserveRatio(true);
                inventarioVisual.getChildren().add(imagemJoia);
            }
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
