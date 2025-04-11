package jsonprotocol;


import org.model.Cursa;
import org.model.Participant;
import org.model.User;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


public class Response implements Serializable {
    private ResponseType type;
    private String errorMessage;
    private User user;
    private List<String> echipe;
    private List<Integer> capacitatiMotor;
    private List<Participant> participanti;
    private List<Cursa> curse;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Response() {
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public List<String> getEchipe() {
        return echipe;
    }

    public void setEchipe(List<String> echipe) {
        this.echipe = echipe;
    }

    public List<Integer> getCapacitatiMotor() {
        return capacitatiMotor;
    }

    public void setCapacitatiMotor(List<Integer> capacitatiMotor) {
        this.capacitatiMotor = capacitatiMotor;
    }

    public List<Participant> getParticipanti() {
        return participanti;
    }

    public void setParticipanti(List<Participant> participanti) {
        this.participanti = participanti;
    }

    public List<Cursa> getCurse() {
        return curse;
    }

    public void setCurse(List<Cursa> curse) {
        this.curse = curse;
    }
}
