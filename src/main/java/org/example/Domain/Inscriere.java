package org.example.Domain;

public class Inscriere extends Entity <Long>{
    Echipa echipa;
    Participant participant;

    public Inscriere(Long id,Echipa echipa, Participant participant) {
        super.id = id;
        this.echipa = echipa;
        this.participant = participant;
    }

    public Echipa getEchipa() {
        return echipa;
    }

    public void setEchipa(Echipa echipa) {
        this.echipa = echipa;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }
}
