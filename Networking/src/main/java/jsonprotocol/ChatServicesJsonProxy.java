package jsonprotocol;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.utils.ServerException;
import org.model.Cursa;
import org.model.User;
import org.service.AppException;
import org.service.IClientObserver;
import org.service.IService;
import org.model.Participant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatServicesJsonProxy implements IService {
    private String host;
    private int port;

    private User currentUser;

    private IClientObserver client;

    private BufferedReader input;
    private PrintWriter output;
    private Gson gsonFormatter;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    private static Logger logger = LogManager.getLogger(ChatServicesJsonProxy.class);

    public ChatServicesJsonProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    private void closeConnection() {
        logger.info("closing connection");
        finished=true;
        try {
            connection.close();
            input.close();
            output.close();
            client=null;
            logger.info("connection closed");
        }
        catch(Exception e){
            logger.error(e);
            logger.error(e.getStackTrace());
        }

    }

    @Override
    public void insertUser(String username, String pass) throws AppException {
        logger.info("start insertUser");
        if(connection == null || connection.isClosed())
            initializeConnection();
        User user = new User(1L,username, pass);
        Request req= JsonProtocolUtils.createAdaugaUserRequest(user);
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.OK){
            return;
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new AppException(err);
        }
        return;
    }

    @Override
    public User userExists(String username, String pass, IClientObserver client) throws AppException {
        logger.info("start userExists");
        if(connection == null || connection.isClosed())
            initializeConnection();
        User user = new User(1L,username, pass);
        Request req= JsonProtocolUtils.createLoginRequest(user);
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.OK){
            this.client=client;
            setCurrentUser(response.getUser());
            return response.getUser();
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new AppException(err);
        }
        return null;
    }

    @Override
    public List<String> getAllTeams() throws AppException {
        logger.info("start getallTeams");
        Request req= JsonProtocolUtils.createGetAllTeamsRequest();
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.OK){
            logger.info("echipe returnate: " + response.getEchipe().size());
            return response.getEchipe();
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new AppException(err);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Integer> getCapacitati() throws AppException {
        logger.info("start getCapacitati");
        Request req= JsonProtocolUtils.createRequestGetCapacitati();
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.OK){
            logger.info("echipe returnate: " + response.getCapacitatiMotor().size());
            return response.getCapacitatiMotor();
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new AppException(err);
        }
        return new ArrayList<>();
    }

    @Override
    public void inscrieParticipant(String nume, String cnp, int cap, String echipa) throws AppException{
        logger.info("start getCapacitati");
        Request req= JsonProtocolUtils.createAdaugaParticipantRequest(new Participant(1L,nume,cap,echipa,cnp));
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.OK){
            logger.info("participant inscris");
            return;
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new AppException(err);
        }
    }

    @Override
    public List<Participant> getAllParticipanti() throws AppException {
        logger.info("start getAllParticipanti");
        Request req= JsonProtocolUtils.createRequestGetAllParticipanti();
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.OK){
            logger.info("participanti returnati: " + response.getParticipanti().size());
            return response.getParticipanti();
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new AppException(err);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Participant> getAllParticipantiByEchipa(String echipa) throws AppException {
        logger.info("start getAllParticipantiByEchipa");
        Request req= JsonProtocolUtils.createRequestGetParticipantiByEchipa(echipa);
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.OK){
            logger.info("curse returnati: " + response.getParticipanti().size());
            return response.getParticipanti();
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new AppException(err);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Cursa> getAllCurse() throws AppException {
        logger.info("start getallCurse");
        Request req= JsonProtocolUtils.createRequestGetAllCurse();
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.OK){
            logger.info("participanti returnati: " + response.getCurse().size());
            return response.getCurse();
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new AppException(err);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Cursa> getAllCurseByCapMotor(int cap) throws AppException {
        logger.info("start getallCurseCapMotor");
        Request req= JsonProtocolUtils.createRequestGetCurseByCap(cap);
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.OK){
            logger.info("curse returnati: " + response.getCurse().size());
            return response.getCurse();
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new AppException(err);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Cursa> getCurseForParticipant(Participant participant)throws AppException {
        logger.info("start getCurseForParticipant");
        Request req= JsonProtocolUtils.createRequestGetCurseByParticipant(participant);
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.OK){
            logger.info("curse returnati: " + response.getCurse().size());
            return response.getCurse();
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new AppException(err);
        }
        return new ArrayList<>();
    }

    @Override
    public void adaugaInscriere(Participant participant, Cursa cursa) throws AppException {
        logger.info("start adaugaInscriere");
        Request req= JsonProtocolUtils.createAdaugaInscriereRequest(participant,cursa);
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.OK){
            logger.info("inscriere adaugata");
            return;
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new AppException(err);
        }
    }

    @Override
    public void logout(User user) throws AppException {
        logger.info("start logout");
        Request req= JsonProtocolUtils.createLogOutRequest(this.currentUser);
        sendRequest(req);
        Response response=readResponse();
        if (response.getType()== ResponseType.OK){
            logger.info("logged out");
            closeConnection();
            return;
        }
        if (response.getType()== ResponseType.ERROR){
            String err=response.getErrorMessage();;
            closeConnection();
            throw new AppException(err);
        }
    }

    private void initializeConnection() {
        try {
            logger.info("start initialise");
            gsonFormatter=new Gson();
            connection=new Socket(host,port);
            output=new PrintWriter(connection.getOutputStream());
            output.flush();
            input=new BufferedReader(new InputStreamReader(connection.getInputStream()));
            finished=false;
            logger.info("before start reader");
            startReader();
        } catch (IOException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
    }

    private void handleUpdate(Response response){
        client.refresh();
    }


    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }

    private boolean isUpdate(Response response){
        return response.getType()== ResponseType.UPDATE;
    }

    private class ReaderThread implements Runnable {
        public void run() {
            while (!finished) {
                try {
                    String responseLine = input.readLine();
                    logger.debug("response received {}", responseLine);
                    Response response = gsonFormatter.fromJson(responseLine, Response.class);
                    if (isUpdate(response)) {
                        handleUpdate(response);
                    } else {
                        try {
                            qresponses.put(response);
                        } catch (InterruptedException e) {
                            logger.error(e);
                            logger.error(e.getStackTrace());
                        }
                    }
                } catch (Exception e) {
                    logger.error("Reading error " + e);
                }
            }
        }
    }
    private void sendRequest(Request request) throws AppException {
        String reqLine=gsonFormatter.toJson(request);
        try {
            output.println(reqLine);
            output.flush();
        } catch (Exception e) {
            throw new AppException("Error sending object "+e);
        }
    }

    private Response readResponse() throws AppException {
        Response response=null;
        logger.info("start read response");
        try{
            response=qresponses.take();
        } catch (InterruptedException e) {
            logger.error(e);
            logger.error(e.getStackTrace());
        }
        return response;
    }

}
