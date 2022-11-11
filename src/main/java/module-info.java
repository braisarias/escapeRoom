module com.liceo.escaperoom {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.liceo.escaperoom to javafx.fxml;
    exports com.liceo.escaperoom;
}