package it.polimi.db2.db2project.ejbmodule.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "telcopackage", schema = "db2_project_schema")
@SecondaryTable(name="customerorder", pkJoinColumns=@PrimaryKeyJoinColumn(name="ID"))
@NamedQuery(name = "TelcoPackage.getPackages", query = "SELECT r FROM TelcoPackage r")
public class TelcoPackage implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "telcoPackage")
    private List<CustomerOrder> orders;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "packages")
    private List<OptionalProduct> optionalProducts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "telcoPackage")
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

    public List<Service> getServices() {
        return services;
    }

//    public List<ValidityPeriod> getValidityPeriods() {
//        return validityPeriods;
//    }

    public List<OptionalProduct> getOptionalProducts() {
        return optionalProducts;
    }

    public void setOrders(List<CustomerOrder> orders) {
        this.orders = orders;
    }

    public void setOptionalProducts(List<OptionalProduct> optionalProducts) {
        this.optionalProducts = optionalProducts;
    }

    public void setValidityPeriods(List<ValidityPeriod> validityPeriods) {
        this.validityPeriods = validityPeriods;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}
