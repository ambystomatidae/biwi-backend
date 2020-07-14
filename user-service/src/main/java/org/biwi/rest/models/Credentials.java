package org.biwi.rest.models;

public class Credentials {
    public String email;
    public String password;

    public Credentials() {
    }

    public boolean isValid() {
        return email != null && password != null;
    }
}