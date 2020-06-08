package org.biwi.rest.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Image extends PanacheEntity {
    private byte[] content;
}
