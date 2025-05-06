package org.model;

import com.google.gson.annotations.SerializedName;

public class Participant extends Entity<Long>{
    String nume;
    @SerializedName("capMotor")
    int capMotor;
    String echipa;
    String cnp;

    public Participant(Long id, String nume, int capMotor, String echipa, String cnp) {
        super.id = id;
        this.nume = nume;
        this.capMotor = capMotor;
        this.echipa = echipa;
        this.cnp = cnp;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getCapMotor() {
        return capMotor;
    }

    public void setCapMotor(int capMotor) {
        this.capMotor = capMotor;
    }

    public String getEchipa() {
        return echipa;
    }

    public void setEchipa(String echipa) {
        this.echipa = echipa;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "nume='" + nume + '\'' +
                ", capMotor=" + capMotor +
                ", echipa=" + echipa +
                ", cnp='" + cnp + '\'' +
                '}';
    }
}
