package it.polimi.db2.db2project.ejbmodule.services;

import it.polimi.db2.db2project.ejbmodule.entities.OptionalProduct;
import it.polimi.db2.db2project.ejbmodule.entities.TelcoPackage;
import it.polimi.db2.db2project.ejbmodule.entities.User;
import it.polimi.db2.db2project.ejbmodule.entities.ValidityPeriod;
import it.polimi.db2.db2project.ejbmodule.exceptions.WrongCredentialsException;

import javax.ejb.Stateless;
import javax.persistence.*;

import java.util.List;

@Stateless
public class ValidityService {
    @PersistenceContext(unitName = "TelcoApplicationEJB")
    private EntityManager em;

    public ValidityPeriod findValidityByID(Integer id){
        ValidityPeriod validityPeriod = em.find(ValidityPeriod.class, id);
        return validityPeriod;
    }
}