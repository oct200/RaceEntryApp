package org.model;
import com.google.gson.annotations.SerializedName;

public class Cursa extends Entity<Long>{
    @SerializedName("numarParticipanti")
    int numarParticipanti;
    @SerializedName("capMotor")
    int capMotor;

    public Cursa(Long id, int numarParticipanti, int capMotor) {
        super.setId(id);
        this.numarParticipanti = numarParticipanti;
        this.capMotor = capMotor;
    }

    public int getNumarParticipanti() {
        return numarParticipanti;
    }

    public void setNumarParticipanti(int numarParticipanti) {
        this.numarParticipanti = numarParticipanti;
    }

    public int getCapMotor() {
        return capMotor;
    }

    public void setCapMotor(int capMotor) {
        this.capMotor = capMotor;
    }


    @Override
    public String toString() {
        return "Cursa{" +
                "numarParticipanti=" + numarParticipanti +
                ", capMotor=" + capMotor +
                '}';
    }


}
