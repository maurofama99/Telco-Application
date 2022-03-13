package it.polimi.db2.db2project.ejbmodule.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class ValidityPeriod implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long duration;
    @Id
    @Column (name = "packageid")
    private Long telcoPackage;
    private int price;

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getDuration() {
        return duration;
    }
}
