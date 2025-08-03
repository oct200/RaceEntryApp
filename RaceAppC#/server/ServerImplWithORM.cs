using model.ORMModel;
using persistence;
using services;
using log4net;
using log4net.Repository.Hierarchy;
using SQLitePCL;
using networking.jsonprotocol;
using System.Collections.Concurrent;
using System.Linq;
using Microsoft.EntityFrameworkCore;

namespace chat.server;
/*
public class ServerImplWithORM : IService
{
    private MydatabaseContext mydatabaseContext;

    private static readonly ILog log = LogManager.GetLogger(typeof(ChatClientJsonWorker));

    private readonly Dictionary<long, IClientObserver> loggedClients = new();

    public ServerImplWithORM() { 
        mydatabaseContext = new MydatabaseContext();
    }

    public void InsertUser(string username, string pass)
    {
        if (mydatabaseContext.Users.Where(u => u.Username == username) != null)
            throw new Exception("Numele de utilizator este deja folosit");

        User user = new User() {Username = username, Parola = pass };
        mydatabaseContext.Users.Add(user);
        mydatabaseContext.SaveChanges();
    }

    public User UserExists(string username, string pass, IClientObserver observer)
    {
        User user = mydatabaseContext.Users.Where(u => u.Username == username && u.Parola == pass).FirstOrDefault();
        if (user != null)
        {
            if (loggedClients.ContainsKey(user.Id))
            {
                throw new Exception("utilizator deja logat");
            }
            loggedClients[user.Id] = observer;
            return user;
        }
        throw new Exception("utilizatorul nu a fost gasit");
    }

    public List<string> GetAllTeams()
    {
        return mydatabaseContext.Participants.Select(p => p.Echipa).Distinct().ToList();
    }

    public List<int> GetCapacitati()
    {
        return mydatabaseContext.Cursas.Select(c => c.CapMotor).Distinct().ToList();
    }

    public void InscrieParticipant(string nume, string cnp, int cap, string echipa)
    {
        log.Info("inceput InscriereParticipant in ServiceJson");
        Participant participant = new Participant() {Nume = nume, CapMotor = cap, Echipa = echipa, Cnp = cnp };
        mydatabaseContext.Participants.Add(participant);
        mydatabaseContext.SaveChanges();
        NotifyObservers();
        log.Info("final InscriereParticipant in ServiceJson");
    }

    public List<Participant> GetAllParticipanti()
    {
        return mydatabaseContext.Participants.ToList();
    }

    public List<Participant> GetAllParticipantiByEchipa(string echipa)
    {
        return GetAllParticipanti().Where(p => p.Echipa == echipa).ToList();
    }

    public List<Cursa> GetAllCurse()
    {
        return mydatabaseContext.Cursas.ToList();
    }

    public List<Cursa> GetAllCurseByCapMotor(int cap)
    {
        return GetAllCurse().Where(c => c.CapMotor == cap).ToList();
    }

    public List<Cursa> GetCurseForParticipant(Participant participant)
    {
        return mydatabaseContext.Cursas
                .Include(c => c.Participants)
                .Where(c => c.CapMotor == participant.CapMotor && !c.Participants.Any(p => p.Id == participant.Id))
                .ToList();
    }

    public void AdaugaInscriere(Participant participant, Cursa cursa)
    {
        log.Info("start adaugaInscriere in ServiceJSON");
        participant = mydatabaseContext.Participants.Where(p => p.Id == participant.Id).FirstOrDefault();
        cursa = mydatabaseContext.Cursas.Where(c => c.Id == cursa.Id).FirstOrDefault();
        cursa.Participants.Add(participant);
        cursa.NumarParticipanti++;
        mydatabaseContext.SaveChanges();
        NotifyObservers();
        log.Info("final adaugaInscriere in ServiceJSON");
    }

    public void Logout(User user)
    {
        if (!loggedClients.ContainsKey(user.Id))
        {
            throw new Exception("utilizatorul nu a fost gasit. Id " + user.Id);
        }
        loggedClients.Remove(user.Id);
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

}*/
public class ServerImplWithORM { }