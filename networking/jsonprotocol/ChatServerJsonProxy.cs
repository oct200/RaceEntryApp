using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Text.Json;
using model.ORMModel;
using services;
using log4net;
using log4net.Repository.Hierarchy;
using System.Text.Json.Serialization;

namespace networking.jsonprotocol;

public class ChatServerJsonProxy : IService
{
    private string host;
    private int port;
    private User CurrentUser;

    private IClientObserver client;
    private NetworkStream stream;
    private TcpClient connection;
    private Queue<Response> responses;
    private volatile bool finished;
    private EventWaitHandle _waitHandle;
    private static readonly ILog log = LogManager.GetLogger(typeof(ChatServerJsonProxy));
    private static readonly JsonSerializerOptions JsonOptions = new()
    {
        ReferenceHandler = System.Text.Json.Serialization.ReferenceHandler.Preserve,
        PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
        Converters = { new JsonStringEnumConverter() }
    };
    public ChatServerJsonProxy(string host, int port)
    {
        this.host = host;
        this.port = port;
        responses = new Queue<Response>();
    }


    private void closeConnection()
    {
        finished = true;
        try
        {
            stream.Close();
            connection.Close();
            _waitHandle.Close();
            client = null;
        }
        catch (Exception e)
        {
            Console.WriteLine(e.StackTrace);
        }
    }

    private void sendRequest(Request request)
    {
        try
        {
            lock (stream)
            {
                string jsonRequest = JsonSerializer.Serialize(request, JsonOptions);
                log.InfoFormat("Sending request {0}", jsonRequest);
                byte[] data = Encoding.UTF8.GetBytes(jsonRequest + "\n"); // Append newline 
                stream.Write(data, 0, data.Length);
                stream.Flush();

            }
        }
        catch (Exception e)
        {
            log.Error("eroare la trimitere obiect " + e.Message);
            throw new Exception("Error sending object " + e);
        }

    }


    private void initializeConnection()
    {
        try
        {
            connection = new TcpClient(host, port);
            stream = connection.GetStream();
            finished = false;
            _waitHandle = new AutoResetEvent(false);
            startReader();
        }
        catch (Exception e)
        {
            Console.WriteLine(e.StackTrace);
        }
    }
    private void startReader()
    {
        Thread tw = new Thread(run);
        tw.Start();
    }


    private void handleUpdate(Response response)
    {
        client.refresh();
    }

    private bool isUpdate(Response response)
    {
        return response.Type == ResponseType.UPDATE;
    }
    private Response readResponse()
    {
        Response response = null;
        try
        {
            _waitHandle.WaitOne();
            lock (responses)
            {
                response = responses.Dequeue();

            }
        }
        catch (Exception e)
        {
            Console.WriteLine(e.StackTrace);
        }
        log.Info("Raspuns primit " + response);
        return response;
    }
    public virtual void run()
    {
        using StreamReader reader = new StreamReader(stream, Encoding.UTF8);
        while (!finished)
        {
            try
            {
                string responseJson = reader.ReadLine();
                if (string.IsNullOrEmpty(responseJson))
                    continue; //nu s-a citit un raspuns valid
                Response response = JsonSerializer.Deserialize<Response>(responseJson, JsonOptions);
                log.Debug("response received " + response);
                if (isUpdate(response))
                {
                    Task.Run(() => handleUpdate(response));
                }
                else
                {
                    lock (responses)
                    {
                        responses.Enqueue(response);
                    }
                    _waitHandle.Set();
                }
            }
            catch (Exception e)
            {
                log.Error("Reading error " + e);
            }

        }
    }

    public User UserExists(string username, string pass, IClientObserver client)
    {
        log.Info("start userExists");
        if (connection == null || !connection.Connected)
            initializeConnection();
        User user = new User() { Id = 1, Username = username, Parola = pass };
        Request req = JsonProtocolUtils.CreateLoginRequest(user);
        sendRequest(req);
        Response response = readResponse();
        if (response.Type == ResponseType.OK)
        {
            this.client = client;
            setCurrentUser(response.User);
            return response.User;
        }
        if (response.Type == ResponseType.ERROR)
        {
            String err = response.ErrorMessage;
            closeConnection();
            throw new Exception(err);
        }
        return null;
    }

    public void setCurrentUser(User currentUser)
    {
        this.CurrentUser = currentUser;
    }

    public void InsertUser(string username, string pass)
    {
        log.Info("start insertUser");
        if (connection == null || !connection.Connected)
            initializeConnection();
        User user = new User() { Id = 1, Username = username, Parola = pass };
        Request req = JsonProtocolUtils.CreateAdaugaUserRequest(user);
        sendRequest(req);
        Response response = readResponse();
        if (response.Type == ResponseType.OK)
        {
            return;
        }
        if (response.Type == ResponseType.ERROR)
        {
            string err = response.ErrorMessage;
            closeConnection();
            throw new Exception(err);
        }
    }

    public List<string> GetAllTeams()
    {
        log.Info("start getAllTeams");
        Request req = JsonProtocolUtils.CreateGetAllTeamsRequest();
        sendRequest(req);
        Response response = readResponse();
        if (response.Type == ResponseType.OK)
        {
            log.Info("echipe returnate: " + response.Echipe.Count);
            return response.Echipe;
        }
        if (response.Type == ResponseType.ERROR)
        {
            string err = response.ErrorMessage;
            closeConnection();
            throw new Exception(err);
        }
        return new List<string>();
    }

    public List<int> GetCapacitati()
    {
        log.Info("start getCapacitati");
        Request req = JsonProtocolUtils.CreateRequestGetCapacitati();
        sendRequest(req);
        Response response = readResponse();
        if (response.Type == ResponseType.OK)
        {
            log.Info("capacitati returnate: " + response.CapacitatiMotor.Count);
            return response.CapacitatiMotor;
        }
        if (response.Type == ResponseType.ERROR)
        {
            string err = response.ErrorMessage;
            closeConnection();
            throw new Exception(err);
        }
        return new List<int>();
    }

    public void InscrieParticipant(string nume, string cnp, int cap, string echipa)
    {
        log.Info("start inscrieParticipant");
        Request req = JsonProtocolUtils.CreateAdaugaParticipantRequest(new Participant() { Id = 1, Nume = nume, CapMotor = cap, Echipa = echipa, Cnp = cnp });
        sendRequest(req);
        Response response = readResponse();
        if (response.Type == ResponseType.OK)
        {
            log.Info("participant inscris");
            return;
        }
        if (response.Type == ResponseType.ERROR)
        {
            string err = response.ErrorMessage;
            closeConnection();
            throw new Exception(err);
        }
    }

    public List<Participant> GetAllParticipanti()
    {
        log.Info("start getAllParticipanti");
        Request req = JsonProtocolUtils.CreateRequestGetAllParticipanti();
        sendRequest(req);
        Response response = readResponse();
        if (response.Type == ResponseType.OK)
        {
            log.Info("participanti returnati: " + response.Participanti.Count);
            return response.Participanti;
        }
        if (response.Type == ResponseType.ERROR)
        {
            string err = response.ErrorMessage;
            closeConnection();
            throw new Exception(err);
        }
        return new List<Participant>();
    }

    public List<Participant> GetAllParticipantiByEchipa(string echipa)
    {
        log.Info("start getAllParticipantiByEchipa");
        Request req = JsonProtocolUtils.CreateRequestGetParticipantiByEchipa(echipa);
        sendRequest(req);
        Response response = readResponse();
        if (response.Type == ResponseType.OK)
        {
            log.Info("participanti returnati: " + response.Participanti.Count);
            return response.Participanti;
        }
        if (response.Type == ResponseType.ERROR)
        {
            string err = response.ErrorMessage;
            closeConnection();
            throw new Exception(err);
        }
        return new List<Participant>();
    }

    public List<Cursa> GetAllCurse()
    {
        log.Info("start getAllCurse");
        Request req = JsonProtocolUtils.CreateRequestGetAllCurse();
        sendRequest(req);
        Response response = readResponse();
        if (response.Type == ResponseType.OK)
        {
            log.Info("curse returnate: " + response.Curse.Count);
            return response.Curse;
        }
        if (response.Type == ResponseType.ERROR)
        {
            string err = response.ErrorMessage;
            closeConnection();
            throw new Exception(err);
        }
        return new List<Cursa>();
    }

    public List<Cursa> GetAllCurseByCapMotor(int cap)
    {
        log.Info("start getAllCurseByCapMotor");
        Request req = JsonProtocolUtils.CreateRequestGetCurseByCap(cap);
        sendRequest(req);
        Response response = readResponse();
        if (response.Type == ResponseType.OK)
        {
            log.Info("curse returnate: " + response.Curse.Count);
            return response.Curse;
        }
        if (response.Type == ResponseType.ERROR)
        {
            string err = response.ErrorMessage;
            closeConnection();
            throw new Exception(err);
        }
        return new List<Cursa>();
    }

    public List<Cursa> GetCurseForParticipant(Participant participant)
    {
        log.Info("start getCurseForParticipant");
        Request req = JsonProtocolUtils.CreateRequestGetCurseByParticipant(participant);
        sendRequest(req);
        Response response = readResponse();
        if (response.Type == ResponseType.OK)
        {
            log.Info("curse returnate: " + response.Curse.Count);
            return response.Curse;
        }
        if (response.Type == ResponseType.ERROR)
        {
            string err = response.ErrorMessage;
            closeConnection();
            throw new Exception(err);
        }
        return new List<Cursa>();
    }

    public void AdaugaInscriere(Participant participant, Cursa cursa)
    {
        log.Info("start adaugaInscriere");
        Request req = JsonProtocolUtils.CreateAdaugaInscriereRequest(participant, cursa);
        sendRequest(req);
        Response response = readResponse();
        if (response.Type == ResponseType.OK)
        {
            log.Info("inscriere adaugata");
            return;
        }
        if (response.Type == ResponseType.ERROR)
        {
            string err = response.ErrorMessage;
            closeConnection();
            throw new Exception(err);
        }
    }

    public void Logout(User user)
    {
        log.Info("start logout");
        Request req = JsonProtocolUtils.CreateLogOutRequest(this.CurrentUser);
        sendRequest(req);
        Response response = readResponse();
        if (response.Type == ResponseType.OK)
        {
            log.Info("logged out");
            closeConnection();
            return;
        }
        if (response.Type == ResponseType.ERROR)
        {
            string err = response.ErrorMessage;
            closeConnection();
            throw new Exception(err);
        }
    }

}