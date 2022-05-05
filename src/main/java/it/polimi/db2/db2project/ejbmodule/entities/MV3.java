package it.polimi.db2.db2project.ejbmodule.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mv3", schema = "db2_project_schema")
public class MV3 {

    @Id
    @GeneratedValue
    private Long id;

    private int optionalID;
    private int amount;

}
