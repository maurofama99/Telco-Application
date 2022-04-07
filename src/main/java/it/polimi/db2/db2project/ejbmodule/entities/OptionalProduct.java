package it.polimi.db2.db2project.ejbmodule.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "optionalproduct", schema = "db2_project_schema")
@NamedQuery(name = "OptionalProduct.getAllOptionalProducts", query = "SELECT r FROM OptionalProduct r")
public class OptionalProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private String name;
    private int fee;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable (name = "orderoptionals", schema = "db2_project_schema", joinColumns = @JoinColumn(name = "optionalID"), inverseJoinColumns = @JoinColumn(name = "orderID"))
    private List<CustomerOrder> orders;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable (name = "packageoptionals", schema = "db2_project_schema", joinColumns = @JoinColumn(name = "optionalID"), inverseJoinColumns = @JoinColumn(name = "packageID"))
    private List<CustomerOrder> packages;

    public Long getId() {
        return id;
    }

    public int getFee() {
        return fee;
    }

    public String getName() {
        return name;
    }

}
