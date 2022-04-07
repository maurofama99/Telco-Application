package it.polimi.db2.db2project.ejbmodule.services;

import it.polimi.db2.db2project.ejbmodule.entities.Service;
import it.polimi.db2.db2project.ejbmodule.entities.TelcoPackage;
import it.polimi.db2.db2project.ejbmodule.entities.User;
import it.polimi.db2.db2project.ejbmodule.exceptions.WrongCredentialsException;

import javax.ejb.Stateless;
import javax.persistence.*;

import java.util.List;

@Stateless
public class ServiceService {
    @PersistenceContext(unitName = "TelcoApplicationEJB")
    private EntityManager em;

    public List<Service> getAllServices() {
        return em.createNamedQuery("Service.getAllServices", Service.class).getResultList();
    }

}
