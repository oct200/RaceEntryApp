using model;
using persistence;
using services;
using log4net;
using log4net.Repository.Hierarchy;
using SQLitePCL;
using networking.jsonprotocol;

namespace chat.server;

 public class ChatServerImpl: IService
 {
    private IRepositoryCursa repoCursa;
    private IRepositoryInscriere repoInscriere;
    private IRepositoryParticipant repoParticipant;
    private IRepositoryUser repoUser;

    private static readonly ILog log = LogManager.GetLogger(typeof(ChatClientJsonWorker));

    private readonly Dictionary<long, IClientObserver> loggedClients = new();

    public ChatServerImpl(IRepositoryCursa repoCursa, IRepositoryInscriere repoInscriere,
        IRepositoryParticipant repoParticipant, IRepositoryUser repoUser)
    {
        this.repoCursa = repoCursa;
        this.repoInscriere = repoInscriere;
        this.repoParticipant = repoParticipant;
        this.repoUser = repoUser;
    }

    public void InsertUser(string username, string pass)
    {
        if (repoUser.getUserByUsername(username) != null)
            throw new Exception("Numele de utilizator este deja folosit");

        User user = new User(1L, username, pass);
        repoUser.Insert(user);
    }

    public User UserExists(string username, string pass, IClientObserver observer)
    {
        User user = repoUser.getUserByUsernameAndPassword(username, pass);
        if (user != null)
        {
            if (loggedClients.ContainsKey(user.id))
            {
                throw new Exception("utilizator deja logat");
            }
            loggedClients[user.id] = observer;
            return user;
        }
        throw new Exception("utilizatorul nu a fost gasit");
    }

    public List<string> GetAllTeams()
    {
        return repoParticipant.getEchipe();
    }

    public List<int> GetCapacitati()
    {
        return repoCursa.getCapacitati();
    }

    public void InscrieParticipant(string nume, string cnp, int cap, string echipa)
    {
        log.Info("inceput InscriereParticipant in ServiceJson");
        Participant participant = new Participant(1L, nume, cap, echipa, cnp);
        repoParticipant.Insert(participant);
        NotifyObservers();
        log.Info("final InscriereParticipant in ServiceJson");
    }

    public List<Participant> GetAllParticipanti()
    {
        return repoParticipant.GetAll();
    }

    public List<Participant> GetAllParticipantiByEchipa(string echipa)
    {
        return repoParticipant.getAllByTeam(echipa);
    }

    public List<Cursa> GetAllCurse()
    {
        return repoCursa.GetAll();
    }

    public List<Cursa> GetAllCurseByCapMotor(int cap)
    {
        return repoCursa.getCurseByCap(cap);
    }

    public List<Cursa> GetCurseForParticipant(Participant participant)
    {
        return repoInscriere.getCursePosibilePentruParticipant(participant);
    }

    public void AdaugaInscriere(Participant participant, Cursa cursa)
    {
        log.Info("start adaugaInscriere in ServiceJSON");
        Inscriere inscriere = new Inscriere(participant, cursa);
        repoInscriere.Insert(inscriere);
        repoCursa.increaseNr(cursa.id);
        NotifyObservers();
        log.Info("final adaugaInscriere in ServiceJSON");
    }

    public void Logout(User user)
    {
        if (!loggedClients.ContainsKey(user.id))
        {
            throw new Exception("utilizatorul nu a fost gasit. Id " + user.id);
        }
        loggedClients.Remove(user.id);
    }

    private void NotifyObservers()
    {
        log.Info("start notify_observers");
        var tasks = new List<Task>();
        foreach (var client in loggedClients)
        {
            tasks.Add(Task.Run(() =>
            {
                try
                {
                    client.Value.refresh();
                }
                catch (Exception e)
                {
                    log.Error("Error notifying client");
                }
            }));
        }
    }

}