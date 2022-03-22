package it.polimi.db2.db2project.ejbmodule.services;

import it.polimi.db2.db2project.ejbmodule.entities.User;
import it.polimi.db2.db2project.ejbmodule.exceptions.WrongCredentialsException;

import javax.ejb.Stateless;
import javax.persistence.*;

import java.util.List;

@Stateless
public class UserService {
    @PersistenceContext(unitName = "TelcoApplicationEJB")
    private EntityManager em;

    public User checkCredentials(String username, String password) throws WrongCredentialsException, NonUniqueResultException {
        List<User> users = null;
        try {
            users = em.createNamedQuery("User.checkCredentials", User.class).setParameter(1, username).setParameter(2, password)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new WrongCredentialsException("Could not verify credentals");
        }
        if (users.isEmpty())
            return null;
        else if (users.size() == 1)
            return users.get(0);
        throw new NonUniqueResultException("More than one user registered with same credentials");

    }

}
