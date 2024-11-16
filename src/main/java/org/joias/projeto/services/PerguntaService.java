package org.joias.projeto.services;

import org.joias.projeto.dao.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PerguntaService {

    /**
     * Busca uma pergunta aleatória do banco de dados que ainda não foi usada.
     *
     * @param perguntasUsadas Lista de IDs de perguntas que já foram usadas na sessão.
     * @return PerguntaComOpcoes contendo a pergunta, resposta correta e outras opções.
     */
    public PerguntaComOpcoes obterPerguntaAleatoria(List<Integer> perguntasUsadas) {
        PerguntaComOpcoes perguntaComOpcoes = null;

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Constrói a lista de IDs para a cláusula IN na consulta SQL
            StringBuilder inClause = new StringBuilder();
            for (int i = 0; i < perguntasUsadas.size(); i++) {
                inClause.append("?");
                if (i < perguntasUsadas.size() - 1) {
                    inClause.append(",");
                }
            }

            String query = "SELECT * FROM perguntas " +
                    (perguntasUsadas.isEmpty() ? "" : "WHERE id NOT IN (" + inClause.toString() + ") ") +
                    "ORDER BY RAND() LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(query);

            // Define os parâmetros para os IDs de perguntas já usadas
            for (int i = 0; i < perguntasUsadas.size(); i++) {
                stmt.setInt(i + 1, perguntasUsadas.get(i));
            }

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String pergunta = rs.getString("pergunta");
                String respostaCorreta = rs.getString("resposta_correta");

                List<String> opcoes = new ArrayList<>();
                opcoes.add(respostaCorreta);
                opcoes.add(rs.getString("opcao_2"));
                opcoes.add(rs.getString("opcao_3"));
                opcoes.add(rs.getString("opcao_4"));

                Collections.shuffle(opcoes, new Random());

                perguntaComOpcoes = new PerguntaComOpcoes(id, pergunta, respostaCorreta, opcoes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return perguntaComOpcoes;
    }

    /**
     * Classe interna para armazenar os dados de uma pergunta, incluindo as opções de resposta.
     */
    public static class PerguntaComOpcoes {
        private final int id;
        private final String pergunta;
        private final String respostaCorreta;
        private final List<String> opcoes;

        public PerguntaComOpcoes(int id, String pergunta, String respostaCorreta, List<String> opcoes) {
            this.id = id;
            this.pergunta = pergunta;
            this.respostaCorreta = respostaCorreta;
            this.opcoes = opcoes;
        }

        public int getId() {
            return id;
        }

        public String getPergunta() {
            return pergunta;
        }

        public String getRespostaCorreta() {
            return respostaCorreta;
        }

        public List<String> getOpcoes() {
            return opcoes;
        }
    }
}
