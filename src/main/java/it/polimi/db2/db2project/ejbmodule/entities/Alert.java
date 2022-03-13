package it.polimi.db2.db2project.ejbmodule.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "alert", schema = "db2_project_schema")
public class Alert implements Serializable {

    @Id
    private Long id;

    public Long getId() {
        return id;
    }
}
