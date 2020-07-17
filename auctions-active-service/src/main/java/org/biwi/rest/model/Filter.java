package org.biwi.rest.model;

import org.biwi.rest.model.AuctionsActive;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

public class Filter {

    @JsonbProperty(value = "lowerPrice", nillable = true)
    private Double lowerPrice;

    @JsonbProperty(value = "higherPrice", nillable = true)
    private Double higherPrice;

    public Filter() {
        this.lowerPrice = null;
        this.higherPrice = null;
    }

    public Filter(double lowerPrice, double higherPrice) {
        this.lowerPrice = lowerPrice;
        this.higherPrice = higherPrice;
    }


    public Double getLowerPrice() {
        return lowerPrice;
    }

    public void setLowerPrice(Double lowerPrice) {
        this.lowerPrice = lowerPrice;
    }

    public Double getHigherPrice() {
        return higherPrice;
    }

    public void setHigherPrice(Double higherPrice) {
        this.higherPrice = higherPrice;
    }

    public boolean matches(AuctionsActive aa) {
        if (lowerPrice != null && aa.getLastBidValue() < lowerPrice) {
            return false;
        }
        if (higherPrice != null && aa.getLastBidValue() > higherPrice) {
            return false;
        }
        return true;
    }

    public boolean byPrice() {
        return lowerPrice != null || higherPrice != null;
    }

}

