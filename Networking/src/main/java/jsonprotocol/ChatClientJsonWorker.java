package jsonprotocol;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.utils.ServerException;
import org.model.Inscriere;
import org.model.Participant;
import org.model.User;
import org.service.AppException;
import org.service.IClientObserver;
import org.service.IService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClientJsonWorker implements Runnable, IClientObserver {
    private IService service;
    private Socket connection;
    private static Response okResponse=JsonProtocolUtils.createOkResponse();


    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private volatile boolean connected;

    private static Logger logger = LogManager.getLogger(ChatClientJsonWorker.class);

    public ChatClientJsonWorker(IService server, Socket connection) {
        this.service = server;
        this.connection = connection;
        gsonFormatter=new Gson();
        try{
            output=new PrintWriter(connection.getOutputStream());
            input=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            connected=true;
        } catch (IOException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
    }

    public void run() {
        while(connected){
            try {
                String requestLine=input.readLine();
                Request request=gsonFormatter.fromJson(requestLine, Request.class);
                Response response= handleRequest(request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                logger.error(e);
                logger.error(e.getStackTrace());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error(e);
                logger.error(e.getStackTrace());
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            logger.error("Error "+e);
        }
    }

    private Response handleRequest(Request request) {
        Response response = null;
        logger.info("Start handle request " + request.getType().toString() );
        if (request.getType() == RequestType.LOGIN) {
            logger.debug("Login request ...{}" + request.getUser());
            User user = request.getUser();
            try {
                if (service.userExists(user.getUsername(), user.getPassword(), this))
                    return okResponse;
                throw new AppException("wrong username or password");
            } catch (Exception e) {
                connected = false;
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.GET_ALL_TEAMS) {
            try {
                return JsonProtocolUtils.createGetTeamResponse(service.getAllTeams());
            } catch (Exception e) {
                connected = false;
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.GET_CAPACITATI) {
            try {
                return JsonProtocolUtils.createGetCapacitatiResponse(service.getCapacitati());
            } catch (Exception e) {
                connected = false;
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.GET_ALL_CURSE) {
            try {
                return JsonProtocolUtils.createCurseResponse(service.getAllCurse());
            } catch (Exception e) {
                connected = false;
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.GET_ALL_PARTICIPANTI) {
            try {
                return JsonProtocolUtils.createParticipantiResponse(service.getAllParticipanti());
            } catch (Exception e) {
                connected = false;
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.GET_CURSE_BY_CAP) {
            try {
                return JsonProtocolUtils.createCurseResponse(service.getAllCurseByCapMotor(request.getCapMotor()));
            } catch (Exception e) {
                connected = false;
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        if (request.getType() == RequestType.GET_PARTICIPANTI_BY_ECHIPE) {
            try {
                return JsonProtocolUtils.createParticipantiResponse(service.getAllParticipantiByEchipa(request.getEchipa()));
            } catch (Exception e) {
                connected = false;
                return JsonProtocolUtils.createErrorResponse(e.getMessage());
            }
        }
        return response;
    }

    private void sendResponse(Response response) throws IOException{
        String responseLine=gsonFormatter.toJson(response);
        logger.debug("sending response "+responseLine);
        synchronized (output) {
            output.println(responseLine);
            output.flush();
        }
    }

    @Override
    public void participantAdded(Participant donatie) throws AppException {

    }

    @Override
    public void inscriereAdded(Inscriere donator) throws AppException {

    }
}
