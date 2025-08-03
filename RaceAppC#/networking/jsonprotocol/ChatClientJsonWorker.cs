using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Text.Json;
using model;
using services;
using log4net;
using services;
using log4net.Repository.Hierarchy;
using log4net.Core;
using System.Text.Json.Serialization;

namespace networking.jsonprotocol;

public class ChatClientJsonWorker : IClientObserver
{
    private IService server;
    private TcpClient connection;

    private NetworkStream stream;
    private volatile bool connected;
    private static readonly ILog log = LogManager.GetLogger(typeof(ChatClientJsonWorker));
    private static readonly JsonSerializerOptions JsonOptions = new()
    {
        //ReferenceHandler = System.Text.Json.Serialization.ReferenceHandler.Preserve,
        PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
        Converters = { new JsonStringEnumConverter() }
    };

    private static Response okResponse = JsonProtocolUtils.CreateOkResponse();
    public ChatClientJsonWorker(IService server, TcpClient connection)
    {
        this.server = server;
        this.connection = connection;
        try
        {
            stream = connection.GetStream();
            connected = true;
        }
        catch (Exception e)
        {
            log.Error(e.StackTrace);
        }
    }

    public virtual void run()
    {
        using StreamReader reader = new StreamReader(stream, Encoding.UTF8);
        while (connected)
        {
            try
            {
                string requestJson = reader.ReadLine(); // Read JSON line-by-line
                if (string.IsNullOrEmpty(requestJson)) continue;
                log.DebugFormat("Received json request {0}", requestJson);
                Request request = JsonSerializer.Deserialize<Request>(requestJson, JsonOptions);
                log.DebugFormat("Deserializaed Request {0} ", request);
                Response response = handleRequest(request);
                if (response != null)
                {
                    sendResponse(response);
                }
            }
            catch (Exception e)
            {
                log.ErrorFormat("run eroare {0}", e.Message);
                if (e.InnerException != null)
                    log.ErrorFormat("run inner error {0}", e.InnerException.Message);
                log.Error(e.StackTrace);
            }

            try
            {
                Thread.Sleep(20);
            }
            catch (Exception e)
            {
                log.Error(e.StackTrace);
            }
        }
        try
        {
            stream.Close();
            connection.Close();
        }
        catch (Exception e)
        {
            log.Error("Error " + e);
        }
    }


    private Response handleRequest(Request request)
    {
        Response response = null;

        if (request.Type == RequestType.LOGIN)
        {
            log.Debug("Login request ...{}" + request.User);
            User user = request.User;
            try
            {
                user = server.UserExists(user.Username, user.Parola, this);
                return JsonProtocolUtils.CreateUserResponse(user);
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse(e.Message);
            }
        }

        if (request.Type == RequestType.LOGOUT)
        {
            log.Debug("Logout request ...{}" + request.User);
            User user = request.User;
            try
            {
                server.Logout(user);
                return JsonProtocolUtils.CreateOkResponse();
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse(e.Message);
            }
        }

        if (request.Type == RequestType.ADAUGA_USER)
        {
            log.Debug("Adaugare user ...{}" + request.User);
            User user = request.User;
            try
            {
                server.InsertUser(user.Username, user.Parola);
                return JsonProtocolUtils.CreateOkResponse();
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse(e.Message);
            }
        }

        if (request.Type == RequestType.ADAUGA_PARTICIPANT)
        {
            log.Debug("Adauga participant ...{}" + request.Participant);
            Participant participant = request.Participant;
            try
            {
                server.InscrieParticipant(participant.Nume, participant.Cnp, participant.CapMotor, participant.Echipa);
                return JsonProtocolUtils.CreateOkResponse();
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse(e.Message);
            }
        }

        if (request.Type == RequestType.ADAUGA_INSCRIERE)
        {
            log.Debug("Adauga inscriere ...{}" + request.Participant + " " + request.Cursa);
            Participant participant = request.Participant;
            Cursa cursa = request.Cursa;
            try
            {
                server.AdaugaInscriere(participant, cursa);
                return JsonProtocolUtils.CreateOkResponse();
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse(e.Message);
            }
        }

        if (request.Type == RequestType.GET_ALL_TEAMS)
        {
            try
            {
                return JsonProtocolUtils.CreateGetTeamResponse(server.GetAllTeams());
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse(e.Message);
            }
        }

        if (request.Type == RequestType.GET_CAPACITATI)
        {
            try
            {
                return JsonProtocolUtils.CreateGetCapacitatiResponse(server.GetCapacitati());
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse(e.Message);
            }
        }

        if (request.Type == RequestType.GET_ALL_CURSE)
        {
            try
            {
                return JsonProtocolUtils.CreateCurseResponse(server.GetAllCurse());
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse(e.Message);
            }
        }

        if (request.Type == RequestType.GET_CURSE_BY_PARTICIPANT)
        {
            try
            {
                return JsonProtocolUtils.CreateCurseResponse(server.GetCurseForParticipant(request.Participant));
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse(e.Message);
            }
        }

        if (request.Type == RequestType.GET_ALL_PARTICIPANTI)
        {
            try
            {
                return JsonProtocolUtils.CreateParticipantiResponse(server.GetAllParticipanti());
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse(e.Message);
            }
        }

        if (request.Type == RequestType.GET_CURSE_BY_CAP)
        {
            try
            {
                return JsonProtocolUtils.CreateCurseResponse(server.GetAllCurseByCapMotor(request.CapMotor));
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse(e.Message);
            }
        }

        if (request.Type == RequestType.GET_PARTICIPANTI_BY_ECHIPE)
        {
            try
            {
                return JsonProtocolUtils.CreateParticipantiResponse(server.GetAllParticipantiByEchipa(request.Echipa));
            }
            catch (Exception e)
            {
                return JsonProtocolUtils.CreateErrorResponse(e.Message);
            }
        }

        return response;
    }


    private void sendResponse(Response response)
    {
        //de modificat pentru json
        string jsonString = JsonSerializer.Serialize(response, JsonOptions);
        log.DebugFormat("sending response {0}", jsonString);
        lock (stream)
        {
            byte[] data = Encoding.UTF8.GetBytes(jsonString + "\n"); // Append newline 
            stream.Write(data, 0, data.Length);
            stream.Flush();
        }

    }

    public void refresh()
    {
        log.Info("sending refresh response");
        Response r = JsonProtocolUtils.CreateUpdateResponse();
        try
        {
            sendResponse(r);
        }
        catch (Exception e)
        {
            log.Error("error sending refresh response from worker");
        }
    }
}