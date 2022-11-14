package com.liceo.escaperoom;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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


    public void generateConfiguration(){
        this.conf = new Configuracion();
    }

    public void refreshInfo(){
        infoAttempts.setText("Quédanche " + this.conf.getAttempts() + " intentos");
        infoMaxAttempts.setText("Tes un total de " + this.conf.getMaxAttempts() + " intentos");
        infoNumber.setText("Número entre " + this.conf.getMinNumber() + " e " + this.conf.getMaxNumber());
    }

    public Configuracion getConf() {
        return conf;
    }

    public void onProbarButtonClick(ActionEvent actionEvent) {
        try {
            int numero = Integer.parseInt(tfNumero.getText());

            System.out.println("boton probar: " + numero);

            if (numero == this.conf.getNumber()){
                feedback.setText("ACERTACHE");
            } else {
                if (this.conf.getAttempts() > 0){
                    feedback.setText("NON ACERTACHES");
                    this.conf.restarIntento();
                }
            }

        }catch (NumberFormatException e) {
            feedback.setText("PON UN NÚMERO!!!");
        } catch (AttemptsException e) {
            feedback.setText("NON PODEMOS SEGUIR");
            botonProbar.setDisable(true);
        } finally {
            this.refreshInfo();
        }


    }
}