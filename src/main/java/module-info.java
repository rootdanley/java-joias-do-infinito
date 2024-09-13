module com.joias.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.joias.demo to javafx.fxml;
    exports com.joias.demo;
}