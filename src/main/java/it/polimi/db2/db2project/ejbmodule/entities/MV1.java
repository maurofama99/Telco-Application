package it.polimi.db2.db2project.ejbmodule.entities;

import javax.persistence.*;

@Entity
@Table(name = "mv1", schema = "db2_project_schema")
// Number of total purchases per package
@NamedQuery(name = "mv1.totpurch", query = "SELECT mv.packageID, mv.packagename, count(mv.packageID) FROM MV1 mv GROUP BY mv.packageID, mv.packagename")
// The number of total purchases per package and validity period.
@NamedQuery(name = "mv1.totpurchvp", query = "SELECT mv.packageID, mv.packagename, mv.vperiod, count(mv.packageID) FROM MV1 mv GROUP BY mv.packageID, mv.packagename, mv.vperiod")
public class MV1 {

    @Id
    @GeneratedValue
    private Long id;

    private int packageID;
    private String packagename;
    private int vperiod;

}
