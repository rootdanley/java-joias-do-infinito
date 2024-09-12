module com.danley.jogojoiasdoinfinito {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.danley.jogojoiasdoinfinito to javafx.fxml;
    exports com.danley.jogojoiasdoinfinito;
}