package com.liceo.escaperoom;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

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


    private void generateConfiguration(){
        this.conf = new Configuracion();
    }

    private void refreshInfo(){
        infoAttempts.setText("Quédanche " + this.conf.getAttempts() + " intentos");
        infoMaxAttempts.setText("Tes un total de " + this.conf.getMaxAttempts() + " intentos");
        infoNumber.setText("Número entre " + this.conf.getMinNumber() + " e " + this.conf.getMaxNumber());
    }

    public void onProbarButtonClick(ActionEvent actionEvent) {
        try {
            int numero = Integer.parseInt(tfNumero.getText());
            System.out.println("boton probar: " + numero);
            if (numero == this.conf.getNumber()){
                this.partidaGanada = true;
                botonProbar.setDisable(true);
            }
            setFeedbackColor(numero);
            this.conf.restarIntento();
            setTextFeedback(numero);
        }catch (NumberFormatException e) {
            feedback.setText("PON UN NÚMERO!!!");
        } catch (AttemptsException e) {
            setTextFeedback();
            botonProbar.setDisable(true);
        } finally {
            this.refreshInfo();
        }


    }

    private  void setTextFeedback(){
        if (this.partidaGanada){
            feedback.setText("ACERTACHE EN " + this.conf.textAttempts() + " INTENTOS!!!");
        } else {
            feedback.setText("NON PODEMOS SEGUIR");
        }
    }
    private void setTextFeedback(int numero) {
        if (this.conf.getNumber() > numero){
            this.feedback.setText("O NÚMERO É MAIOR");
            return;
        }
        if (this.conf.getNumber() == numero){
            feedback.setText("ACERTACHE EN " + this.conf.textAttempts() + " INTENTOS!!!");
            return;
        }
        if (this.conf.getNumber() < numero){
            this.feedback.setText("O NÚMERO É MENOR");
            return;
        }
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

    public void inicializaPartida(){
        this.partidaGanada = false;
        generateConfiguration();
        refreshInfo();
        this.botonProbar.setDisable(false);
        this.feedback.setText("");
        System.out.println("Partida inicializada: " + this.conf);
    }
    public void onButtonRestartClick (ActionEvent actionEvent){
        inicializaPartida();
    }

}