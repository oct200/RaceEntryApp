package org.example;


import org.model.Cursa;

<<<<<<< Updated upstream
public interface CursaRepositoryInterface extends RepoInterface<Cursa,Long> {
=======
import java.util.List;

public interface CursaRepositoryInterface extends RepoInterface<Cursa,Long> {
    public List<Cursa> getCurseByCap(int cap);
    public void increaseNr(Long id);
    public List<Integer> getCapacitati();
>>>>>>> Stashed changes
}
