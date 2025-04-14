package org.service;

import org.model.Cursa;
import org.model.Participant;
import org.model.User;

import java.util.List;

public interface IService {
    void insertUser(String username, String pass) throws AppException;
    User userExists(String username, String pass, IClientObserver client) throws AppException;
    List<String> getAllTeams() throws AppException;
    List<Integer> getCapacitati() throws AppException;
    void inscrieParticipant(String nume, String cnp, int cap, String echipa)throws AppException;
    List<Participant> getAllParticipanti()throws AppException;
    List<Participant> getAllParticipantiByEchipa(String echipa)throws AppException;
    List<Cursa> getAllCurse()throws AppException;
    List<Cursa> getAllCurseByCapMotor(int cap)throws AppException;
    List<Cursa> getCurseForParticipant(Participant participant)throws AppException;
    void adaugaInscriere(Participant participant, Cursa cursa)throws AppException;
    void logout(User user) throws AppException;
}
