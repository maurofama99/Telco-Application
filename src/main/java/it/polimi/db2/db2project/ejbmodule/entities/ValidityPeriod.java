package it.polimi.db2.db2project.ejbmodule.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "validityperiod", schema = "db2_project_schema")
public class ValidityPeriod implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int duration;

    @ManyToOne
    @JoinColumn (name = "packageID",  referencedColumnName="ID")
    private TelcoPackage telcoPackage;

    private int price;

    public ValidityPeriod(int duration, int price) {
        this.duration = duration;
        this.price = price;
    }

    public ValidityPeriod() {}

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public int getPrice() {
        return price;
    }
}
