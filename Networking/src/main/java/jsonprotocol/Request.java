package jsonprotocol;


import org.model.User;

import java.util.Arrays;

public class Request {
    private RequestType type;
    private User user;
    private User[] listaUseri;
    private String echipa;
    private int capMotor;

    public Request(){}
    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User[] getListaUseri() {
        return listaUseri;
    }

    public void setListaUseri(User[] listaUseri) {
        this.listaUseri = listaUseri;
    }

    public String getEchipa() {
        return echipa;
    }

    public void setEchipa(String echipa) {
        this.echipa = echipa;
    }

    public int getCapMotor() {
        return capMotor;
    }

    public void setCapMotor(int capMotor) {
        this.capMotor = capMotor;
    }
}
