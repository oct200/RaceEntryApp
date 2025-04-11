package jsonprotocol;


import org.model.Cursa;
import org.model.Participant;
import org.model.User;

import java.util.List;

public class JsonProtocolUtils {
    public static Request createLoginRequest(User user){
        Request req=new Request();
        req.setType(RequestType.LOGIN);
        req.setUser(user);
        return req;
    }

    public static Response createUserResponse(User user){
        Response res=new Response();
        res.setType(ResponseType.USER_EXISTS);
        res.setUser(user);
        return res;
    }

    public static Response createOkResponse(){
        Response resp=new Response();
        resp.setType(ResponseType.OK);
        return resp;
    }

    public static Response createErrorResponse(String errorMessage){
        Response resp=new Response();
        resp.setType(ResponseType.ERROR);
        resp.setErrorMessage(errorMessage);
        return resp;
    }
    public static Request createGetAllTeamsRequest(){
        Request req=new Request();
        req.setType(RequestType.GET_ALL_TEAMS);
        return req;
    }

    public static Response createGetTeamResponse(List<String> teams){
        Response resp=new Response();
        resp.setType(ResponseType.OK);
        resp.setEchipe(teams);
        return resp;
    }

    public static Request createRequestGetCapacitati(){
        Request req=new Request();
        req.setType(RequestType.GET_CAPACITATI);
        return req;
    }
    public static Request createRequestGetAllParticipanti(){
        Request req=new Request();
        req.setType(RequestType.GET_ALL_PARTICIPANTI);
        return req;
    }
    public static Request createRequestGetAllCurse(){
        Request req=new Request();
        req.setType(RequestType.GET_ALL_CURSE);
        return req;
    }

    public static Request createRequestGetCurseByCap(int cap){
        Request req=new Request();
        req.setType(RequestType.GET_CURSE_BY_CAP);
        req.setCapMotor(cap);
        return req;
    }

    public static Request createRequestGetParticipantiByEchipa(String echipa){
        Request req=new Request();
        req.setType(RequestType.GET_PARTICIPANTI_BY_ECHIPE);
        req.setEchipa(echipa);
        return req;
    }

    public static Response createGetCapacitatiResponse(List<Integer> cap){
        Response resp=new Response();
        resp.setType(ResponseType.OK);
        resp.setCapacitatiMotor(cap);
        return resp;
    }

    public static Response createParticipantiResponse(List<Participant> par){
        Response resp=new Response();
        resp.setType(ResponseType.OK);
        resp.setParticipanti(par);
        return resp;
    }

    public static Response createCurseResponse(List<Cursa> curse){
        Response resp=new Response();
        resp.setType(ResponseType.OK);
        resp.setCurse(curse);
        return resp;
    }

}
