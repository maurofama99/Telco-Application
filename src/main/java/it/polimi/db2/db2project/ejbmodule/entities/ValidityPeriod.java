package it.polimi.db2.db2project.ejbmodule.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "validityperiod", schema = "db2_project_schema")
public class ValidityPeriod implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long duration;

    @ManyToOne
    @Id
    @JoinColumn (name = "packageID",  referencedColumnName="ID")
    private TelcoPackage telcoPackage;

    private int price;

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getDuration() {
        return duration;
    }
}
