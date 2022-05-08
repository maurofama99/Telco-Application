package it.polimi.db2.db2project.ejbmodule.entities;

import jdk.jfr.Name;

import javax.persistence.*;

@Entity
@Table(name = "mv3", schema = "db2_project_schema")
// Bestseller optional product
@NamedQuery(name = "mv3.bestseller", query = "SELECT o FROM MV3 o WHERE o.amount = (SELECT MAX(oo.amount) FROM MV3 oo)")
public class MV3 {

    @Id
    @GeneratedValue
    private Long id;

    private int optionalID;
    private int amount;

    public int getOptionalID() {
        return optionalID;
    }

    public int getAmount() {
        return amount;
    }
}
