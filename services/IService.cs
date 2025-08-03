using model;

namespace services;

public interface IService   
{   void InsertUser(string username, string pass);
    User UserExists(string username, string pass, IClientObserver client);
    List<string> GetAllTeams();
    List<int> GetCapacitati();
    void InscrieParticipant(string nume, string cnp, int cap, string echipa);
    List<Participant> GetAllParticipanti();
    List<Participant> GetAllParticipantiByEchipa(string echipa);
    List<Cursa> GetAllCurse();
    List<Cursa> GetAllCurseByCapMotor(int cap);
    List<Cursa> GetCurseForParticipant(Participant participant);
    void AdaugaInscriere(Participant participant, Cursa cursa);
    void Logout(User user);
}