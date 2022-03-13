package it.polimi.db2.db2project.ejbmodule.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Alert implements Serializable {

    @Id
    private Long id;

    public Long getId() {
        return id;
    }
}
