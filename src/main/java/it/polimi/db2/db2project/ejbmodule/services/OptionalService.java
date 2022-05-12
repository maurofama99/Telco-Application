package it.polimi.db2.db2project.ejbmodule.services;

import it.polimi.db2.db2project.ejbmodule.entities.Employee;
import it.polimi.db2.db2project.ejbmodule.entities.OptionalProduct;
import it.polimi.db2.db2project.ejbmodule.entities.TelcoPackage;
import it.polimi.db2.db2project.ejbmodule.entities.User;
import it.polimi.db2.db2project.ejbmodule.exceptions.WrongCredentialsException;

import javax.ejb.Stateless;
import javax.persistence.*;

import java.util.List;

@Stateless
public class OptionalService {
    @PersistenceContext(unitName = "TelcoApplicationEJB")
    private EntityManager em;

    public OptionalProduct findOptionalByID(int id){
        return em.find(OptionalProduct.class, id);
    }

    public List<OptionalProduct> getAllOptionalProducts(){
        return em.createNamedQuery("OptionalProduct.getAllOptionalProducts", OptionalProduct.class).getResultList();
    }

    public void createOptional(String name, int fee) {
        OptionalProduct optionalProduct = new OptionalProduct(name, fee);
        em.persist(optionalProduct);
    }
}