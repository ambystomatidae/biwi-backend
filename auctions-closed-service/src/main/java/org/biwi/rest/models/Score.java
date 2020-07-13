package org.biwi.rest.models;

public class Score {
    public Double rating;

    public Score() {
    }

    public boolean isValid() {
        return rating != null && rating >= 0 && rating <=5 ;
    }

}
