package org.example;

<<<<<<< Updated upstream
=======

import org.model.Participant;
>>>>>>> Stashed changes

import org.model.Participant;

<<<<<<< Updated upstream
public interface ParticipantRepositoryInterface extends RepoInterface<Participant,Long> {
=======
import java.util.List;

public interface ParticipantRepositoryInterface extends RepoInterface<Participant,Long> {
    public List<Participant> getAllByTeam(String team);
    public List<String> getEchipe();
>>>>>>> Stashed changes
}
