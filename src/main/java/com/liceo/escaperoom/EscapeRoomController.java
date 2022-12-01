package com.liceo.escaperoom;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.Properties;

public class EscapeRoomController {
    @FXML
    private Label feedback;

    private Configuracion conf = null;
    @FXML
    private Label infoNumber;
    @FXML
    private Label infoMaxAttempts;
    @FXML
    private Label infoAttempts;
    @FXML
    private TextField tfNumero;

    @FXML
    private Button botonProbar;
    private boolean partidaGanada=false;

    private BufferedWriter bwLog;

    private void generateConfiguration(){
        Properties confProp = new Properties();
        try {
            InputStream confFile = Files.newInputStream(Paths.get("escapeRoom.properties"));
            confProp.load(confFile);
        } catch (IOException e) {
            this.conf = new Configuracion();
            return;
        }
        int min = Configuracion.MIN_NUM;
        int max = Configuracion.MAX_NUM;
        int attempts = Configuracion.MAX_ATTEMPTS;

        try {
            min = Integer.parseInt(confProp.getProperty(Configuracion.MIN_NUM_P));
        } catch (NumberFormatException e){

        }
        try {
            max = Integer.parseInt(confProp.getProperty(Configuracion.MAX_NUM_P));
        } catch (NumberFormatException e){

        }
        try{
            attempts = Integer.parseInt(confProp.getProperty(Configuracion.MAX_ATTEMPTS_P));
        } catch (NumberFormatException e){

        }



        this.conf = new Configuracion(max, min, attempts);
    }

    private void refreshInfo(){
        String attempts = "Quédanche " + this.conf.getAttempts() + " intentos";
        String maxAttempts = "Tes un total de " + this.conf.getMaxAttempts() + " intentos";
        String number = "Número entre " + this.conf.getMinNumber() + " e " + this.conf.getMaxNumber();

        infoAttempts.setText(attempts);
        infoMaxAttempts.setText(maxAttempts);
        infoNumber.setText(number);

        imprimeLog(attempts);
        imprimeLog(maxAttempts);
        imprimeLog(number);
    }

    public void onProbarButtonClick(ActionEvent actionEvent) {
        try {
            int numero = Integer.parseInt(tfNumero.getText());
            imprimeLog("boton probar: " + numero);
            if (numero == this.conf.getNumber()){
                this.partidaGanada = true;
                imprimeLog("Partida ganada = true");
                botonProbar.setDisable(true);
            }
            setFeedbackColor(numero);
            this.conf.restarIntento();
            setTextFeedback(numero);
        }catch (NumberFormatException e) {
            setTextFeedback("PON UN NÚMERO!!!");
        } catch (AttemptsException e) {
            setTextFeedback();
            botonProbar.setDisable(true);
        } finally {
            this.refreshInfo();
        }


    }

    private  void setTextFeedback(){
        String texto = "NON PODEMOS SEGUIR";
        if (this.partidaGanada){
            texto = "ACERTACHE EN " + this.conf.textAttempts() + " INTENTOS!!!";
        }
        feedback.setText(texto);
        imprimeLog("feedback: " + texto);
    }
    private void setTextFeedback(int numero) {
        String texto = "";
        if (this.conf.getNumber() > numero){
            texto = "O NÚMERO É MAIOR";
        }
        if (this.conf.getNumber() == numero){
            texto = "ACERTACHE EN " + this.conf.textAttempts() + " INTENTOS!!!";
        }
        if (this.conf.getNumber() < numero){
            texto = "O NÚMERO É MENOR";
        }
        this.feedback.setText(texto);
        imprimeLog("feedback: " + texto);
    }
    private void setTextFeedback(String msg){
        this.feedback.setText(msg);
        imprimeLog("feedback: " + msg);
    }

    private void setFeedbackColor(int n) {
        int dif = Math.abs(this.conf.getNumber()-n);

        if(dif == 0){
            feedback.setTextFill(Color.GREEN);
            return;
        }
        if (dif > 20){
            this.feedback.setTextFill(Color.BLUE);
            return;
        }
        if (dif <= 20 && dif >= 10){
            this.feedback.setTextFill(Color.ORANGE);
            return;
        }
        if (dif < 10){
            this.feedback.setTextFill(Color.RED);
            return;
        }
    }

    public void inicializaPartida() throws java.io.IOException{
        this.bwLog = Files.newBufferedWriter(Paths.get("escapeRooom.log"));
        this.partidaGanada = false;
        generateConfiguration();
        refreshInfo();
        this.botonProbar.setDisable(false);
        this.feedback.setText("");
        imprimeLog("Partida inicializada: " + this.conf);
    }

    private void imprimeLog(String s) {
        System.out.println(s);
        LocalDateTime dt = LocalDateTime.now();
        try {
            this.bwLog.write("[" + dt.format(DateTimeFormatter.ISO_DATE_TIME) + "] " + s + "\n");
            this.bwLog.flush();
        } catch (IOException e){
            System.out.println("Non puidemos escribir no ficheiro de log");
        }
    }

    public void onButtonRestartClick (ActionEvent actionEvent) throws java.io.IOException{
        inicializaPartida();
    }

}