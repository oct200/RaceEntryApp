package org.server;

import org.example.CursaRepositoryInterface;
import org.example.InscriereRepositoryInterface;
import org.example.ParticipantRepositoryInterface;
import org.example.UserRepositoryInterface;
import org.example.utils.ServerException;
import org.model.Cursa;
import org.model.Inscriere;
import org.model.Participant;
import org.model.User;
import org.service.AppException;
import org.service.IClientObserver;
import org.service.IService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceJSON implements IService {
    private final CursaRepositoryInterface repoCursa;
    private final InscriereRepositoryInterface repoInscriere;
    private final ParticipantRepositoryInterface repoParticipant;
    private final UserRepositoryInterface repoUser;

    private final Map<Long, IClientObserver> loggedClients = new HashMap<Long, IClientObserver>();

    public ServiceJSON(CursaRepositoryInterface repoCursa, InscriereRepositoryInterface repoInscriere,
                            ParticipantRepositoryInterface repoParticipant, UserRepositoryInterface repoUser) {
        this.repoCursa = repoCursa;
        this.repoInscriere = repoInscriere;
        this.repoParticipant = repoParticipant;
        this.repoUser = repoUser;
    }

    public void insertUser(String username, String pass) {
        if (repoUser.getUserByUsername(username) != null)
            throw new RuntimeException("Numele de utilizator este deja folosit");

        User user = new User(1L, username, pass);
        repoUser.insert(user);
    }

    public boolean userExists(String username, String pass,IClientObserver observer) throws AppException {
        User user = repoUser.getUserByUsernameAndPassword(username, pass);
        if(user != null){
            if(loggedClients.get(user.getId()) != null) {
                throw new AppException("utilizator deja logat");
            }
            loggedClients.put(user.getId(), observer);
            return true;
        }
        return false;
    }

    public List<String> getAllTeams() {
        return repoParticipant.getEchipe();
    }

    public List<Integer> getCapacitati() {
        return repoCursa.getCapacitati();
    }

    public void inscrieParticipant(String nume, String cnp, int cap, String echipa) {
        Participant participant = new Participant(1L, nume, cap, echipa, cnp);
        repoParticipant.insert(participant);
    }

    public List<Participant> getAllParticipanti() {
        return repoParticipant.getAll();
    }

    public List<Participant> getAllParticipantiByEchipa(String echipa) {
        return repoParticipant.getAllByTeam(echipa);
    }

    public List<Cursa> getAllCurse() {
        return repoCursa.getAll();
    }

    public List<Cursa> getAllCurseByCapMotor(int cap) {
        return repoCursa.getCurseByCap(cap);
    }

    public List<Cursa> getCurseForParticipant(Participant participant) {
        return repoInscriere.getCursePosibilePentruParticipant(participant);
    }

    public void adaugaInscriere(Participant participant, Cursa cursa) {
        Inscriere inscriere = new Inscriere(participant, cursa);
        repoInscriere.insert(inscriere);
        repoCursa.increaseNr(cursa.getId());
    }
}
