package org.joias.projeto.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PartidaController {

    @FXML
    private Label nomeJogador1;
    @FXML
    private Label nomeJogador2;

    @FXML
    private ImageView imagemJogador1;
    @FXML
    private ImageView imagemJogador2;

    /**
     * Método para definir os personagens dos jogadores na tela de partida.
     *
     * @param personagemJogador1 Nome do personagem selecionado pelo Jogador 1.
     * @param personagemJogador2 Nome do personagem selecionado pelo Jogador 2.
     */
    public void setPersonagensJogadores(String personagemJogador1, String personagemJogador2) {
        nomeJogador1.setText(personagemJogador1);
        nomeJogador2.setText(personagemJogador2);

        Image imagem1 = carregarImagemPersonagem(personagemJogador1);
        Image imagem2 = carregarImagemPersonagem(personagemJogador2);

        if (imagem1 != null) {
            imagemJogador1.setImage(imagem1);
        } else {
            System.err.println("Imagem para " + personagemJogador1 + " não encontrada.");
        }

        if (imagem2 != null) {
            imagemJogador2.setImage(imagem2);
        } else {
            System.err.println("Imagem para " + personagemJogador2 + " não encontrada.");
        }
    }

    private Image carregarImagemPersonagem(String personagem) {
        String caminhoImagem = "/" + personagem.toLowerCase().replace(" ", "_") + ".png";
        try {
            var resource = getClass().getResource(caminhoImagem);
            if (resource != null) {
                return new Image(resource.toExternalForm());
            } else {
                System.err.println("Recurso não encontrado: " + caminhoImagem);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar a imagem: " + caminhoImagem);
            e.printStackTrace();
            return null;
        }
    }
}
