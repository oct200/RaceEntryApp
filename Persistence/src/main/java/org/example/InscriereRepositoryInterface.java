package org.example;


import org.model.Cursa;
import org.model.Inscriere;
import org.model.Pair;
import org.model.Participant;

import java.util.List;

public interface InscriereRepositoryInterface extends RepoInterface<Inscriere, Pair<Long,Long>> {
    List<Participant> getParticipantiInscrisiByCursaId(Long id);
    public List<Cursa> getCursePosibilePentruParticipant(Participant participant);
}
