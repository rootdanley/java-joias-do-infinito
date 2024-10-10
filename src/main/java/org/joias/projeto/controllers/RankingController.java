package org.joias.projeto.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.joias.projeto.dao.DatabaseConnection;
import org.joias.projeto.models.Vencedor;
import org.joias.projeto.dao.VencedorDAO;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class RankingController {

    @FXML
    private TableView<Vencedor> tableVencedores;
    @FXML
    private TableColumn<Vencedor, Integer> colunaId;
    @FXML
    private TableColumn<Vencedor, String> colunaNome;

    private ObservableList<Vencedor> vencedoresData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurando as colunas da tabela
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nomeJogador"));

        // Carregar os dados dos vencedores
        carregarVencedores();
    }

    private void carregarVencedores() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            VencedorDAO vencedorDAO = new VencedorDAO(conn);
            List<Vencedor> vencedores = vencedorDAO.buscarTodosVencedores();
            vencedoresData.setAll(vencedores);
            tableVencedores.setItems(vencedoresData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}