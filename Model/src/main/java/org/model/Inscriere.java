package org.model;


public class Inscriere extends Entity <Pair<Long,Long>>{
    Cursa cursa;
    Participant participant;

    public Inscriere(Pair<Long,Long> id,Cursa cursa, Participant participant) {
        super.id = id;
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
