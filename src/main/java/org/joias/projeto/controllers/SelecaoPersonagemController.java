package org.joias.projeto.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SelecaoPersonagemController {

    @FXML
    private Label tituloJogador;

    @FXML
    private ImageView imagemPantera;
    @FXML
    private ImageView imagemFeiticeira;
    @FXML
    private ImageView imagemFerro;
    @FXML
    private ImageView imagemViuva;

    @FXML
    private Button botaoSelecionar;
    @FXML
    private Button botaoVoltar;
    @FXML
    private Button botaoIniciarPartida;
    @FXML
    private Button botaoSair;

    private int jogadorAtual = 1;
    private String personagemSelecionado = null;
    private String personagemJogador1 = null;
    private String personagemJogador2 = null;
    private Set<String> personagensEscolhidos = new HashSet<>();

    @FXML
    public void initialize() {
        botaoIniciarPartida.setDisable(true);  // Botão começa desabilitado

        // Carregar as imagens dos personagens
        try {
            imagemPantera.setImage(new Image(getClass().getResource("/pantera_negra.png").toExternalForm()));
            imagemFeiticeira.setImage(new Image(getClass().getResource("/feiticeira_escarlate.png").toExternalForm()));
            imagemFerro.setImage(new Image(getClass().getResource("/homem_de_ferro.png").toExternalForm()));
            imagemViuva.setImage(new Image(getClass().getResource("/viuva_negra.png").toExternalForm()));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar as imagens dos personagens. Verifique se os arquivos de imagem existem no caminho especificado.");
        }

        imagemPantera.setOnMouseClicked(event -> selecionarPersonagem("Pantera Negra", imagemPantera));
        imagemFeiticeira.setOnMouseClicked(event -> selecionarPersonagem("Feiticeira Escarlate", imagemFeiticeira));
        imagemFerro.setOnMouseClicked(event -> selecionarPersonagem("Homem de Ferro", imagemFerro));
        imagemViuva.setOnMouseClicked(event -> selecionarPersonagem("Viúva Negra", imagemViuva));
    }

    private void selecionarPersonagem(String personagem, ImageView imagemPersonagem) {
        if (!personagensEscolhidos.contains(personagem)) {
            personagemSelecionado = personagem;
            atualizarSelecaoVisual(imagemPersonagem);
            tituloJogador.setText(personagem);
            botaoSelecionar.setDisable(false);
        } else {
            tituloJogador.setText("Personagem já escolhido, selecione outro.");
        }
    }

    @FXML
    private void onSelecionarHeroi() {
        if (personagemSelecionado != null) {
            personagensEscolhidos.add(personagemSelecionado);

            if (jogadorAtual == 1) {
                personagemJogador1 = personagemSelecionado;
                jogadorAtual = 2;
                tituloJogador.setText("Jogador #2, selecione seu herói");
                desabilitarPersonagemSelecionado();
                personagemSelecionado = null;
            } else {
                personagemJogador2 = personagemSelecionado;
                desabilitarPersonagemSelecionado();
                botaoIniciarPartida.setDisable(false);
                botaoSelecionar.setDisable(true);
                botaoVoltar.setDisable(true);
                tituloJogador.setText("Clique em 'Iniciar Partida' para começar!");
            }

            botaoSelecionar.setDisable(true);
        } else {
            tituloJogador.setText("Por favor, selecione um herói.");
        }
    }


    @FXML
    private void onVoltar() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/joias/projeto/inicio.fxml"));
            Stage stage = (Stage) botaoVoltar.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar a tela inicial. Verifique o caminho do FXML.");
        }
    }

    @FXML
    private void onSair() {
        Platform.exit();
    }

    private void atualizarSelecaoVisual(ImageView imagemSelecionada) {
        imagemPantera.setStyle("");
        imagemFeiticeira.setStyle("");
        imagemFerro.setStyle("");
        imagemViuva.setStyle("");

        imagemSelecionada.setStyle("-fx-border-color: green; -fx-border-width: 4px;");
    }

    private void desabilitarPersonagemSelecionado() {
        ColorAdjust desaturate = new ColorAdjust();
        desaturate.setSaturation(-1);

        if (personagemSelecionado.equals("Pantera Negra")) {
            imagemPantera.setEffect(desaturate);
            imagemPantera.setDisable(true);
        } else if (personagemSelecionado.equals("Feiticeira Escarlate")) {
            imagemFeiticeira.setEffect(desaturate);
            imagemFeiticeira.setDisable(true);
        } else if (personagemSelecionado.equals("Homem de Ferro")) {
            imagemFerro.setEffect(desaturate);
            imagemFerro.setDisable(true);
        } else if (personagemSelecionado.equals("Viúva Negra")) {
            imagemViuva.setEffect(desaturate);
            imagemViuva.setDisable(true);
        }
    }

    @FXML
    private void iniciarPartida() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/joias/projeto/partida.fxml"));
            Parent root = loader.load();

            // Passa os personagens selecionados para o controlador da tela de partida
            PartidaController partidaController = loader.getController();
            partidaController.setPersonagensJogadores(personagemJogador1, personagemJogador2);

            // Exibe a tela de partida
            Stage stage = (Stage) botaoIniciarPartida.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar a tela da partida. Verifique o caminho do FXML.");
        }
    }
}
