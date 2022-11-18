package com.liceo.escaperoom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EscapeRoomApp extends Application {

    EscapeRoomController controller;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EscapeRoomApp.class.getResource("escapeRoom-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1024, 720);
        stage.setTitle("EscapeRoom!");
        stage.setScene(scene);
        stage.show();

        this.controller = fxmlLoader.getController();
        this.controller.inicializaPartida();
    }

    public static void main(String[] args) {
        launch();
    }
}