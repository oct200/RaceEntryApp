package org.example.utils;

import jsonprotocol.ChatClientJsonWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.service.IService;

import java.net.Socket;

public class JSONConcurrentServer extends AbsConcurrentServer{
    private IService chatServer;
    private static Logger logger = LogManager.getLogger(JSONConcurrentServer.class);
    public JSONConcurrentServer(int port, IService chatServer) {
        super(port);
        this.chatServer = chatServer;
        logger.info("Chat-ChatJsonConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ChatClientJsonWorker worker=new ChatClientJsonWorker(chatServer, client);
        Thread tw=new Thread(worker);
        return tw;
    }
}