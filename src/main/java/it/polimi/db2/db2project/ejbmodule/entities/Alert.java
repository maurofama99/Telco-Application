package it.polimi.db2.db2project.ejbmodule.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "alert", schema = "db2_project_schema")
@NamedQuery(name = "Alert.everybody", query = "SELECT r FROM Alert r")
public class Alert implements Serializable {

    @Id
    private Long id;


    private int amount;
    private LocalDate rejectiondate;

    @OneToOne
    private User user;

    public Long getId() {
        return id;
    }


    public int getAmount() {
        return amount;
    }

    public LocalDate getRejectiondate() {
        return rejectiondate;
    }

    public User getUser() {
        return user;
    }
}
