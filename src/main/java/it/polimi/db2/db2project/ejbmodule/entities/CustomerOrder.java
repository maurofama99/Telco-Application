package it.polimi.db2.db2project.ejbmodule.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
public class CustomerOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalDate startDate;
    private boolean orderStatus; // 0 -> invalid, 1 -> valid
    private int amount;

    @ManyToOne
    @JoinColumn (name = "userid")
    private User user;

    @ManyToOne
    @JoinColumn (name = "telcopackageid")
    private TelcoPackage telcoPackage;

    @ManyToOne
    @JoinColumn (name = "duration")
    private ValidityPeriod validityPeriod;

    @ManyToMany (mappedBy = "optionalinorder")
    private List<OptionalProduct> optionalProducts;

    public Long getId() {
        return id;
    }
}
