package com.liceo.escaperoom;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class EscapeRoomController {
    @FXML
    private Label feedback;


    public void onProbarButtonClick(ActionEvent actionEvent) {
        System.out.println("boton probar");
        feedback.setText("probando");
    }
}