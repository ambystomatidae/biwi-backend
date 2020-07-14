package org.biwi.rest.models;

public class Bid {

    public double value;
    public String idUser;

    public Bid(String idUser, double value) {
        this.idUser = idUser;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "value=" + value +
                ", idUser='" + idUser + '\'' +
                '}';
    }
}