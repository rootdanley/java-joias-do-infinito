module org.joias.projeto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Se você estiver usando JDBC para conectar ao MySQL

    exports org.joias.projeto; // Se você precisa exportar o pacote principal

    // Abre o pacote 'controllers' para o módulo 'javafx.fxml'
    opens org.joias.projeto.controllers to javafx.fxml;
}
