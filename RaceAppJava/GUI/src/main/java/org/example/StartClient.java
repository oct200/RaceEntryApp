package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jsonprotocol.ChatServicesJsonProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.service.IService;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.PrimitiveIterator;
import java.util.Properties;

public class StartClient extends Application {
    private Stage primaryStage;

    private static int defaultChatPort = 55556;
    private static String defaultServer = "localhost";

    private static Logger logger = LogManager.getLogger(StartClient.class);

    public void start(Stage primaryStage) throws Exception {
        logger.debug("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(new FileReader("chatclient.properties"));
            logger.info("Client properties set {} ",clientProps);
            clientProps.list(System.out);
        } catch (IOException e) {
            logger.error("Cannot find chatclient.properties " + e);
            logger.debug("Looking for chatclient.properties in folder {}",(new File(".")).getAbsolutePath());
            return;
        }
        String serverIP = clientProps.getProperty("chat.server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("chat.server.port"));
        } catch (NumberFormatException ex) {
            logger.error("Wrong port number " + ex.getMessage());
            logger.debug("Using default port: " + defaultChatPort);
        }
        logger.info("Using server IP " + serverIP);
        logger.info("Using server port " + serverPort);

        IService server = new ChatServicesJsonProxy(serverIP, serverPort);

        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("autentificare.fxml"));
        Parent root=loader.load();


        AutentificareController ctrl =
                loader.<AutentificareController>getController();
        ctrl.setServiceAplicatie(server);

        primaryStage.setTitle("MPP chat");
        primaryStage.setScene(new Scene(root, 300, 130));
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);  // Calls Application.launch() to start the JavaFX application
    }


}