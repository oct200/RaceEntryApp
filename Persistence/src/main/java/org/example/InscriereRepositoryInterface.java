package org.example;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream:src/main/java/org/example/Repository/InscriereRepositoryInterface.java
import org.example.Domain.Inscriere;
import org.example.Domain.Pair;
import org.example.Domain.Participant;
=======
>>>>>>> Stashed changes:Persistence/src/main/java/org/example/InscriereRepositoryInterface.java
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes

import org.model.Cursa;
import org.model.Inscriere;
import org.model.Pair;
import org.model.Participant;

import java.util.List;

public interface InscriereRepositoryInterface extends RepoInterface<Inscriere, Pair<Long,Long>> {
    List<Participant> getParticipantiInscrisiByCursaId(Long id);
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
    public List<Cursa> getCursePosibilePentruParticipant(Participant participant);
>>>>>>> Stashed changes
=======
    public List<Cursa> getCursePosibilePentruParticipant(Participant participant);
>>>>>>> Stashed changes
}
