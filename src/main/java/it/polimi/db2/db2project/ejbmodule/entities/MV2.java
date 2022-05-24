package it.polimi.db2.db2project.ejbmodule.entities;

import javax.persistence.*;

@Entity
@Table(name = "mv2", schema = "db2_project_schema")
// The total value of sales per package with the optional products.
@NamedQuery(name = "mv2.totvalueop", query = "SELECT mv.packageID, mv.packagename, sum(mv.amount) FROM MV2 mv GROUP BY mv.packageID, mv.packagename")
// The total value of sales per package without the optional products.
@NamedQuery(name = "mv2.totvaluenoop", query = "SELECT mv.packageID, mv.packagename, sum(mv.noopamount) FROM MV2 mv GROUP BY mv.packageID, mv.packagename")
// Number of optional product sold together with each service package.
@NamedQuery(name = "mv2.opperpackage", query = "SELECT mv.packageID, mv.packagename, avg(mv.optionals) FROM MV2 mv GROUP BY mv.packageID, mv.packagename")
public class MV2 {

    @Id
    @GeneratedValue
    private Long id;

    private int packageID;
    private String packagename;
    private int optionals;
    private int amount;
    private int noopamount;
    private int orderID;

}
