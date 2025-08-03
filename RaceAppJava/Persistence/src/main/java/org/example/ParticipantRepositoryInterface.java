package org.example;


import org.model.Participant;

import java.util.List;

public interface ParticipantRepositoryInterface extends RepoInterface<Participant,Long> {
    public List<Participant> getAllByTeam(String team);
    public List<String> getEchipe();
}
