package org.example.Repository;

import org.example.Domain.Inscriere;
import org.example.Domain.Pair;
import org.example.Domain.Participant;

import java.lang.ref.ReferenceQueue;
import java.util.List;
import java.util.function.LongFunction;

public interface InscriereRepositoryInterface extends RepoInterface<Inscriere, Pair<Long,Long>> {
    List<Participant> getParticipantiInscrisiByCursaId(Long id);
}
