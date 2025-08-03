package org.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class AbstractServer {
    private int port;
    private ServerSocket server=null;
    private static Logger logger = LogManager.getLogger(AbstractServer.class);
    public AbstractServer( int port){
        this.port=port;
    }

    public void start() throws ServerException {
        try{
            server=new ServerSocket(port);
            logger.info("starting server ...");
            while(true){
                logger.info("Waiting for clients ...");
                Socket client=server.accept();
                logger.info("Client connected ...");
                processRequest(client);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new ServerException("Starting server errror ",e);
        }finally {
            System.out.println("sdddddddd");
            stop();
        }
    }

    protected abstract  void processRequest(Socket client);
    public void stop() throws ServerException {
        try {
            server.close();
        } catch (IOException e) {
            throw new ServerException("Closing server error ", e);
        }
    }
}