package org.biwi.requests;

import org.biwi.external.ShortDescription;

import javax.json.bind.annotation.JsonbProperty;
import java.util.List;

public class Filter {
    @JsonbProperty(value = "categories", nillable = true)
    private List<String> categories;

    @JsonbProperty(value = "lowerPrice", nillable = true)
    private Double lowerPrice;

    @JsonbProperty(value = "higherPrice", nillable = true)
    private Double higherPrice;

    public Filter() {
        this.categories = null;
        this.lowerPrice = null;
        this.higherPrice = null;
    }

    public Filter(List<String> categories, double lowerPrice, double higherPrice) {
        this.categories = categories;
        this.lowerPrice = lowerPrice;
        this.higherPrice = higherPrice;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
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

    public boolean matches(ShortDescription sd) {
        if (lowerPrice != null && sd.getStartingPrice() < lowerPrice) {
            return false;
        }
        if (higherPrice != null && sd.getStartingPrice() > higherPrice) {
            return false;
        }
        if (categories != null) {
            // TODO
        }
        return true;
    }

    public boolean byPrice() {
        return lowerPrice != null || higherPrice != null;
    }

    public boolean byCategories() {
        return this.categories != null;
    }
}
