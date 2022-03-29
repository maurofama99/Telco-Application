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

    /**
     * Check if user credentials are correct and returns the corresponding User object if true
     * @return User
     * @throws WrongCredentialsException if could not verify credentials
     * @throws NonUniqueResultException if more than one user registered with same credentials
     */
    public User checkCredentials(String username, String password) throws WrongCredentialsException, NonUniqueResultException {
        List<User> users = null;
        try {
            users = em.createNamedQuery("User.checkCredentials", User.class).setParameter(1, username).setParameter(2, password)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new WrongCredentialsException("Could not verify credentials");
        }
        if (users.isEmpty())
            return null;
        else if (users.size() == 1)
            return users.get(0);
        throw new NonUniqueResultException("More than one user registered with same credentials");
    }

    public boolean checkSameUser(String username) throws WrongCredentialsException {
        List<User> users = null;
        try {
            users = em.createNamedQuery("User.checkSameUser", User.class).setParameter(1, username)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new WrongCredentialsException("Could not verify credentials");
        }

        return users.isEmpty();
    }

    public void registerUser(String username, String password, String mail){
        User newUser = new User();
        newUser.setName(username);
        newUser.setEmail(mail);
        newUser.setPassword(password);
        em.persist(newUser);
    }

}
