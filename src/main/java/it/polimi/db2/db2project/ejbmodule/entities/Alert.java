package it.polimi.db2.db2project.ejbmodule.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "alert", schema = "db2_project_schema")
public class Alert implements Serializable {

    @Id
    private Long id;
    private int amount;
    private LocalDate rejectiondate;

    @OneToOne
    private User user;

    public Long getId() {
        return id;
    }
}
