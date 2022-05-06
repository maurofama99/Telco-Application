package it.polimi.db2.db2project.ejbmodule.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mv1", schema = "db2_project_schema")
public class MV1 {

    @Id
    @GeneratedValue
    private Long id;

    private int packageID;
    private String packagename;
    private int vperiod;

}
