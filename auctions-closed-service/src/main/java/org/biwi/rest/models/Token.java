package org.biwi.rest.models;

import org.jose4j.json.internal.json_simple.JSONObject;

public class Token {
    static final double refreshAt = 0.95;

    public String access_token;
    public long createdAt;
    public long expires_in;
    public String refresh_token;

    public Token() {
    }

    public Token(JSONObject json) {
        this.access_token = (String) json.get("access_token");
        this.refresh_token = (String) json.get("refresh_token");
        this.createdAt = System.currentTimeMillis();
        this.expires_in = (long) json.get("expires_in") * 1000;
    }

    public boolean isValid() {
        return System.currentTimeMillis() - createdAt < expires_in * refreshAt;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + access_token + '\'' +
                ", createdAt=" + createdAt +
                ", expiresIn=" + expires_in +
                ", refreshToken='" + refresh_token + '\'' +
                '}';
    }

    public boolean isValidRefresh() {
        return refresh_token != null;
    }
}