package org.server;

import jsonprotocol.ChatClientJsonWorker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceJSON implements IService {
    private final CursaRepositoryInterface repoCursa;
    private final InscriereRepositoryInterface repoInscriere;
    private final ParticipantRepositoryInterface repoParticipant;
    private final UserRepositoryInterface repoUser;
    private final int defaultThreads = 5;
    private static Logger logger = LogManager.getLogger(ServiceJSON.class);

    private final Map<Long, IClientObserver> loggedClients = new HashMap<Long, IClientObserver>();

    public ServiceJSON(CursaRepositoryInterface repoCursa, InscriereRepositoryInterface repoInscriere,
                            ParticipantRepositoryInterface repoParticipant, UserRepositoryInterface repoUser) {
        this.repoCursa = repoCursa;
        this.repoInscriere = repoInscriere;
        this.repoParticipant = repoParticipant;
        this.repoUser = repoUser;
    }

    public void insertUser(String username, String pass) throws AppException{
        if (repoUser.getUserByUsername(username) != null)
            throw new RuntimeException("Numele de utilizator este deja folosit");

        User user = new User(1L, username, pass);
        repoUser.insert(user);
    }

    public User userExists(String username, String pass,IClientObserver observer) throws AppException {
        User user = repoUser.getUserByUsernameAndPassword(username, pass);
        if(user != null){
            if(loggedClients.get(user.getId()) != null) {
                throw new AppException("utilizator deja logat");
            }
            loggedClients.put(user.getId(), observer);
            return user;
        }
        throw new AppException("utilizatorul nu a fost gasit");
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
        notify_observers();
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
        logger.info("start adaugaInscriere in ServiceJSON");
        Inscriere inscriere = new Inscriere(participant, cursa);
        repoInscriere.insert(inscriere);
        repoCursa.increaseNr(cursa.getId());
        notify_observers();
    }

    @Override
    public void logout(User user) throws AppException {
        if(!loggedClients.containsKey(user.getId())){
            throw new AppException("utilizatorul nu a fost gasit. Id " + user.getId());
        }
        loggedClients.remove(user.getId());
    }

    private void notify_observers()
    {
        logger.info("start notify_observers");
        ExecutorService executor = Executors.newFixedThreadPool(defaultThreads);
        for(Long voluntarId : loggedClients.keySet())
        {
            executor.execute(()->{
                IClientObserver client = loggedClients.get(voluntarId);
                try{
                    client.refresh();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
