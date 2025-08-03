package jsonprotocol;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.service.IService;
import org.example.utils.AbsConcurrentServer;

import java.net.Socket;

public class ChatJsonConcurrentServer extends AbsConcurrentServer {
    private IService chatServer;
    private static Logger logger = LogManager.getLogger(ChatJsonConcurrentServer.class);
    public ChatJsonConcurrentServer(int port, IService chatServer) {
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
