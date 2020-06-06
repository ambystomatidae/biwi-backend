package org.biwi.rest.models;

import java.time.LocalDateTime;

public interface ShortDescription {
    String getName();
    double getStartingPrice();
    LocalDateTime getBeginDate();
    Image getMainImage();
}
