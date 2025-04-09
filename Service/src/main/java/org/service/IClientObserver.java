package org.service;

import org.model.Inscriere;
import org.model.Participant;

public interface IClientObserver {
    void participantAdded(Participant donatie) throws AppException;
    void inscriereAdded(Inscriere donator) throws AppException;
}
