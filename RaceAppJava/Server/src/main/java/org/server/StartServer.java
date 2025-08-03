package org.server;

import jsonprotocol.ChatJsonConcurrentServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.*;
import org.example.utils.AbstractServer;
import org.example.utils.ServerException;
import org.service.IService;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StartServer {
    private static int defaultPort=55556;
    private static Logger logger = LogManager.getLogger(StartServer.class);
    public static void main (String[]args) {
        // UserRepository userRepo=new UserRepositoryMock();
        Properties serverProps = new Properties();
        try {
            serverProps.load(new FileReader("db.config"));
            logger.info("Server properties set. {} ", serverProps);
            serverProps.list(System.out);
        } catch (IOException e) {
            logger.error("Cannot find chatserver.properties " + e);
            logger.debug("Looking for file in " + (new File(".")).getAbsolutePath());
            return;
        }
        IService serverCreat = createService(serverProps);
        int chatServerPort = defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("chat.server.port"));
        } catch (NumberFormatException nef) {
            logger.error("Wrong  Port Number" + nef.getMessage());
            logger.debug("Using default port " + defaultPort);
        }
        logger.debug("Starting server on port: " + chatServerPort);
        AbstractServer server = new ChatJsonConcurrentServer(chatServerPort, serverCreat);
        try {
            server.start();
        } catch (ServerException e) {
            logger.error("Error starting the server" + e.getMessage());
        }
    }

    public static IService createService(Properties dbProperties){
        CursaRepositoryInterface ci = new CursaDBRepository(dbProperties);
        ParticipantRepositoryInterface pi = new ParticipantDBRepository(dbProperties);
        InscriereRepositoryInterface ii = new InscriereDBRepository(dbProperties,pi,ci);
        UserRepositoryInterface ui = new UserDBRepository(dbProperties);

        return new ServiceJSON(ci,ii,pi,ui);
    }
}