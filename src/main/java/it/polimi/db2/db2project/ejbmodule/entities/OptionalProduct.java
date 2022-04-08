package it.polimi.db2.db2project.ejbmodule.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
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


    @ManyToMany (mappedBy = "optionalProducts", fetch = FetchType.EAGER)
    private List<CustomerOrder> orders;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable (name = "packageoptionals", schema = "db2_project_schema", joinColumns = @JoinColumn(name = "optionalID"), inverseJoinColumns = @JoinColumn(name = "packageID"))
    private List<CustomerOrder> packages;

    public OptionalProduct() {
    }

    public OptionalProduct(String name, int fee) {
        this.name = name;
        this.fee = fee;
    }

    public Long getId() {
        return id;
    }

    public int getFee() {
        return fee;
    }

    public String getName() {
        return name;
    }

    public void addOrder(CustomerOrder customerOrder){
        if(orders == null){
            orders = new ArrayList<>();
        }
        this.orders.add(customerOrder);
    }

}
