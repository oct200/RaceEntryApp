package org.model;


public class Inscriere extends Entity <Pair<Long,Long>>{
    Cursa cursa;
    Participant participant;

    public Inscriere(Participant participant,Cursa cursa) {
        super.id = new Pair<>(cursa.getId(), participant.getId());
        this.cursa = cursa;
        this.participant = participant;
    }

    public Cursa getCursa() {
        return cursa;
    }

    public void setCursa(Cursa echipa) {
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
