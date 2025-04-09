package org.model;

public class Echipa extends Entity<Long>{
    String nume;

    public Echipa(Long id,String nume) {
        super.id=id;
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "Echipa{" +
                "nume='" + nume + '\'' +
                '}';
    }
}
