package org.joias.projeto.models;

import java.time.LocalDateTime;

public class Vencedor {
    private int id;
    private String nomeJogador;

    // Construtor padrão
    public Vencedor() {}

    // Construtor com parâmetros
    public Vencedor(String nomeJogador) {
        this.nomeJogador = nomeJogador;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public void setNomeJogador(String nomeJogador) {
        this.nomeJogador = nomeJogador;
    }

    @Override
    public String toString() {
        return "Vencedor{" +
                "id=" + id +
                ", nomeJogador='" + nomeJogador + '\'' +
                '}';
    }
}
