using model.ORMModel;

namespace networking.jsonprotocol;

public class JsonProtocolUtils {
    public static Response CreateOkResponse()
    {
        Response resp = new Response();
        resp.Type = ResponseType.OK;
        return resp;
    }

    public static Request CreateLoginRequest(User user)
    {
        Request req = new Request();
        req.Type = RequestType.LOGIN;
        req.User = user;
        return req;
    }

    public static Response CreateUserResponse(User user)
    {
        Response res = new Response();
        res.Type = ResponseType.OK;
        res.User = user;
        return res;
    }

    public static Response CreateErrorResponse(string errorMessage)
    {
        Response resp = new Response();
        resp.Type = ResponseType.ERROR;
        resp.ErrorMessage = errorMessage;
        return resp;
    }

    public static Request CreateLogOutRequest(User user)
    {
        Request req = new Request();
        req.Type = RequestType.LOGOUT;
        req.User = user;
        return req;
    }

    public static Request CreateAdaugaParticipantRequest(Participant participant)
    {
        Request req = new Request();
        req.Type = RequestType.ADAUGA_PARTICIPANT;
        req.Participant = participant;
        return req;
    }

    public static Request CreateAdaugaInscriereRequest(Participant participant, Cursa cursa)
    {
        Request req = new Request();
        req.Type = RequestType.ADAUGA_INSCRIERE;
        req.Participant = participant;
        req.Cursa = cursa;
        return req;
    }

    public static Request CreateAdaugaUserRequest(User user)
    {
        Request req = new Request();
        req.Type = RequestType.ADAUGA_USER;
        req.User = user;
        return req;
    }

    public static Response CreateUpdateResponse()
    {
        Response res = new Response();
        res.Type = ResponseType.UPDATE;
        return res;
    }

    public static Request CreateRequestGetCurseByParticipant(Participant participant)
    {
        Request req = new Request();
        req.Type = RequestType.GET_CURSE_BY_PARTICIPANT;
        req.Participant = participant;
        return req;
    }

    public static Request CreateGetAllTeamsRequest()
    {
        Request req = new Request();
        req.Type = RequestType.GET_ALL_TEAMS;
        return req;
    }

    public static Response CreateGetTeamResponse(List<string> teams)
    {
        Response resp = new Response();
        resp.Type = ResponseType.OK;
        resp.Echipe = teams;
        return resp;
    }

    public static Request CreateRequestGetCapacitati()
    {
        Request req = new Request();
        req.Type = RequestType.GET_CAPACITATI;
        return req;
    }

    public static Request CreateRequestGetAllParticipanti()
    {
        Request req = new Request();
        req.Type = RequestType.GET_ALL_PARTICIPANTI;
        return req;
    }

    public static Request CreateRequestGetAllCurse()
    {
        Request req = new Request();
        req.Type = RequestType.GET_ALL_CURSE;
        return req;
    }

    public static Request CreateRequestGetCurseByCap(int cap)
    {
        Request req = new Request();
        req.Type = RequestType.GET_CURSE_BY_CAP;
        req.CapMotor = cap;
        return req;
    }

    public static Request CreateRequestGetParticipantiByEchipa(string echipa)
    {
        Request req = new Request();
        req.Type = RequestType.GET_PARTICIPANTI_BY_ECHIPE;
        req.Echipa = echipa;
        return req;
    }

    public static Response CreateGetCapacitatiResponse(List<int> capacitati)
    {
        Response resp = new Response();
        resp.Type = ResponseType.OK;
        resp.CapacitatiMotor = capacitati;
        return resp;
    }

    public static Response CreateParticipantiResponse(List<Participant> participants)
    {
        Response resp = new Response();
        resp.Type = ResponseType.OK;
        resp.Participanti = participants;
        return resp;
    }

    public static Response CreateCurseResponse(List<Cursa> curse)
    {
        Response resp = new Response();
        resp.Type = ResponseType.OK;
        resp.Curse = curse;
        return resp;
    }

}
