package it.polimi.db2.db2project.ejbmodule.services;

import it.polimi.db2.db2project.ejbmodule.entities.Employee;
import it.polimi.db2.db2project.ejbmodule.entities.User;
import it.polimi.db2.db2project.ejbmodule.exceptions.WrongCredentialsException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.List;

@Stateless
public class EmployeeService {
    @PersistenceContext(unitName = "TelcoApplicationEJB")
    private EntityManager em;

    /**
     * Check if employee credentials are correct and returns the corresponding User object if true
     * @return Employee
     * @throws WrongCredentialsException if could not verify credentials
     * @throws NonUniqueResultException if more than one user registered with same credentials
     */
    public Employee checkEmployeeCredentials(String username, String password) throws WrongCredentialsException, NonUniqueResultException {
        List<Employee> users = null;
        try {
            users = em.createNamedQuery("Employee.checkEmployeeCredentials", Employee.class).setParameter(1, username).setParameter(2, password).getResultList();
        } catch (PersistenceException e) {
            throw new WrongCredentialsException("Could not verify credentials");
        }
        if (users.isEmpty())
            return null;
        else if (users.size() == 1)
            return users.get(0);
        throw new NonUniqueResultException("More than one user registered with same credentials");
    }

}

