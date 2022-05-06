package it.polimi.db2.db2project.ejbmodule.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mv2", schema = "db2_project_schema")
public class MV2 {

    @Id
    @GeneratedValue
    private Long id;

    private int packageID;
    private String packagename;
    private int optionals;
    private int amount;
    private int noopamount;

}
