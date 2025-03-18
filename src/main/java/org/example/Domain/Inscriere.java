package org.example.Domain;


public class Inscriere extends Entity <Pair<Long,Long>>{
    Cursa cursa;
    Participant participant;

    public Inscriere(Pair<Long,Long> id,Cursa cursa, Participant participant) {
        super.id = id;
        this.cursa = cursa;
        this.participant = participant;
    }

    public Cursa getEchipa() {
        return cursa;
    }

    public void setEchipa(Cursa echipa) {
        this.cursa = echipa;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    @Override
    public String toString() {
        return "Inscriere{" +
                "cursa=" + cursa +
                ", participant=" + participant +
                '}';
    }
}
