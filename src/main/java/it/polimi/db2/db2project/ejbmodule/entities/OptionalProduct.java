package it.polimi.db2.db2project.ejbmodule.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class OptionalProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    private String name;
    private int fee;

    @ManyToMany
    @JoinTable (name = "orderoptionals", schema = "db2_project_schema", joinColumns = @JoinColumn(name = "optionald"), inverseJoinColumns = @JoinColumn(name = "orderid"))
    private List<CustomerOrder> orders;

    public Long getId() {
        return id;
    }
}
