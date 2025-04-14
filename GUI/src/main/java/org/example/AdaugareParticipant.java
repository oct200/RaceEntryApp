package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.service.IService;

import java.io.IOException;


public class AdaugareParticipant {
    private static final Logger logger = LogManager.getLogger(AdaugareParticipant.class);
    private IService proxy;

    @FXML
    private TextField textFieldNume;
    @FXML
    private TextField textFieldEchipa;
    @FXML
    private TextField textFieldCNP;
    @FXML
    private TextField textFieldCapMotor;

    public void setServiceAplicatie(IService proxy) {
        this.proxy = proxy;
    }

    private int getCapMotorFromText() {
        try {
            return Integer.parseInt(textFieldCapMotor.getText().trim());
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    @FXML
    private void handleInscriere() {
        logger.info("Incepere handleInscriere");
        String nume = textFieldNume.getText().trim();
        String cnp = textFieldCNP.getText().trim();
        String echipa = textFieldEchipa.getText().trim();
        int capMotor = getCapMotorFromText();
        if(capMotor < 0){
            logger.info("capacitate motor gresita");
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Eroare","introduceti toate datele");
            return;
        }
        if (nume.isEmpty() || cnp.isEmpty() || echipa.isEmpty()) {
            logger.info("introduceti toate datele");
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Eroare","introduceti toate datele");
            return;
        }

        try {
            proxy.inscrieParticipant(nume, cnp, capMotor, echipa);
            MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Succes", "introdus cu succes");
            closeWindow();
        }
        catch (Exception ex) {
            logger.error("eroare la insert user " + ex.getMessage());
            MessageAlert.showMessage(null, Alert.AlertType.ERROR,"Eroare","eroare la introducerea participantului " + ex.getMessage());
        }
    }

    @FXML
    protected void handleInapoi() {
        logger.info("click inapoi");
        Stage stage = (Stage) textFieldNume.getScene().getWindow();
        stage.close();
    }

    private void closeWindow(){
        Stage stage = (Stage) textFieldNume.getScene().getWindow();
        stage.close();
    }

}
