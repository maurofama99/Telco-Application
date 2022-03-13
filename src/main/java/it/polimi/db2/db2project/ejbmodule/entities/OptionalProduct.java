package it.polimi.db2.db2project.ejbmodule.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "optionalproduct", schema = "db2_project_schema")
public class OptionalProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;
    private String name;
    private int fee;

    @ManyToMany
    @JoinTable (name = "orderoptionals", schema = "db2_project_schema", joinColumns = @JoinColumn(name = "optionalID"), inverseJoinColumns = @JoinColumn(name = "orderid"))
    private List<CustomerOrder> orders;

    public Long getId() {
        return id;
    }
}

// ogni volta che un utente non paga, subito dopo vengono contati i suoi ordini in stato suspended, se sono tre viene creato un alert

// ogni volta che un utente non paga, il suo insolvent counter incrementa, quando insolvent = 3, controlli se già non esiste un alert di questo user, in caso crei alert