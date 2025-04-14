package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jsonprotocol.ChatClientJsonWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.model.User;
import org.service.IClientObserver;
import org.service.IService;

public class AutentificareController {
    IService proxy;
    @FXML
    public TextField textFieldUsername;
    @FXML
    public TextField textFieldPassword;
    @FXML
    public Button buttonLogin;
    @FXML
    public Button buttonRegister;
    private static Logger logger = LogManager.getLogger(AutentificareController.class);

    public void setServiceAplicatie(IService proxy) {
        this.proxy  = proxy;
    }

    @FXML
    protected void clickLogIn(){
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            MessageAlert.showMessage(null, AlertType.ERROR,"Eroare","introduceti parola si username-ul");
            return;
        }

        try {
            logger.info(1);
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getClassLoader().getResource("principal.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            logger.info(2);
            logger.info(3);
            Principal comntroller = fxmlLoader.getController();
            if ((proxy.userExists(username, password,comntroller))  != null) {
                Stage primaryStage = (Stage) textFieldPassword.getScene().getWindow();
                comntroller.setServiceAplicatie(proxy);
                primaryStage.setScene(scene);
                primaryStage.show();
            } else {
                MessageAlert.showMessage(null, AlertType.ERROR,"Eroare","utilizatorul nu a fost gasit");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            MessageAlert.showMessage(null, AlertType.ERROR,"Eroare",ex.getMessage());
        }

    }

    @FXML
    protected void clickSignUp(){
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            MessageAlert.showMessage(null, AlertType.ERROR,"Eroare","introduceti parola si username-ul");
            return;
        }
        try {
            proxy.insertUser(username, password);
            MessageAlert.showMessage(null, AlertType.CONFIRMATION,"Introdus","utilizatorul a fost introdus cu succes");
        } catch (Exception ex) {
            MessageAlert.showMessage(null, AlertType.ERROR,"Eroare",ex.getMessage());
        }

    }
}
