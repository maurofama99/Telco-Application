package it.polimi.db2.db2project.ejbmodule.services;

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

    public OptionalProduct findOptionalByID(Integer id){
        OptionalProduct optionalProduct = em.find(OptionalProduct.class, id);
        return optionalProduct;
    }
}