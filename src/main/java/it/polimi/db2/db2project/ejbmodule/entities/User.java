package it.polimi.db2.db2project.ejbmodule.entities;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

/**
 * The persistent class for the user database table.
 */
@Entity
@Table(name = "user", schema = "db2_project_schema")
@NamedQuery(name = "User.checkCredentials", query = "SELECT r FROM User r  WHERE r.name = ?1 and r.password = ?2")
@NamedQuery(name = "User.checkSameUser", query = "SELECT r FROM User r  WHERE r.name = ?1")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private int insolvent;
    private int alert;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<CustomerOrder> orders;

    // per adesso non stiamo mettendo alert, quindi da user non possiamo arrivare ad alert

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getInsolvent() {
        return insolvent;
    }

    public void incrementInsolvent() {
        this.insolvent++;
    }
}

// ogni volta che un utente non paga, subito dopo vengono contati i suoi ordini in stato suspended, se sono tre viene creato un alert

// ogni volta che un utente non paga, il suo insolvent counter incrementa, quando insolvent = 3, controlli se già non esiste un alert di questo user, in caso crei alert
