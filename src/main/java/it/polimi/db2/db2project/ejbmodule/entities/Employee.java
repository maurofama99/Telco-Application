package it.polimi.db2.db2project.ejbmodule.entities;

import jakarta.persistence.*;

import java.io.Serializable;

/**
 * The persistent class for the employee database table.
 */
@Entity
@Table(name = "employee", schema = "db2_project_schema")
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
