package it.polimi.db2.db2project.ejbmodule.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "telcopackage", schema = "db2_project_schema")
public class TelcoPackage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "telcoPackage")
    private List<CustomerOrder> orders;

    @ManyToMany(mappedBy = "packages")
    private List<OptionalProduct> optionalProducts;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "telcoPackage")
    private List<ValidityPeriod> validityPeriods;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "telcoPackage")
    private List<Service> services;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
