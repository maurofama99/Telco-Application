package it.polimi.db2.db2project.ejbmodule.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "customerorder", schema = "db2_project_schema")
@NamedQuery(name = "CustomerOrder.failed", query = "SELECT r FROM CustomerOrder r  WHERE r.user.name = ?1 and r.orderStatus = false")
@NamedQuery(name = "CustomerOrder.suspended", query = "SELECT r FROM CustomerOrder r  WHERE r.orderStatus = false")
public class CustomerOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate date;
    private LocalDate startDate;
    private boolean orderStatus; // 0 -> invalid, 1 -> valid
    private int amount;
    private int noopamount; // total amount without considering optionals fee

    @ManyToOne
    @JoinColumn (name = "userid")
    private User user;

    @ManyToOne
    @JoinColumn (name = "packageID", referencedColumnName="id")
    private TelcoPackage telcoPackage;

    @ManyToOne
    @JoinColumn(name="validityperiodID", referencedColumnName="id")
    private ValidityPeriod validityPeriod;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable (name = "orderoptionals", schema = "db2_project_schema", joinColumns = @JoinColumn(name = "orderID"), inverseJoinColumns = @JoinColumn(name = "optionalID"))
    private List<OptionalProduct> optionalProducts;


    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public boolean isOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TelcoPackage getTelcoPackage() {
        return telcoPackage;
    }

    public void setTelcoPackage(TelcoPackage telcoPackage) {
        this.telcoPackage = telcoPackage;
    }

    public ValidityPeriod getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(ValidityPeriod validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public List<OptionalProduct> getOptionalProducts() {
        return optionalProducts;
    }

    public void setOptionalProducts(List<OptionalProduct> optionalProducts) {
        this.optionalProducts = optionalProducts;
    }

    public void setNoopAmount(int noopamount) {
        this.noopamount = noopamount;
    }

    public int getNoopamount() {
        return noopamount;
    }
}
