package org.biwi.rest.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Bid extends PanacheEntity {

    private double value;
    private LocalDateTime timeStamp;
    private String idUser;

    public Bid(){
    }

    public Bid(String idUser, double value){
        this.idUser = idUser;
        this.value = value;
        this.timeStamp = LocalDateTime.now();
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
