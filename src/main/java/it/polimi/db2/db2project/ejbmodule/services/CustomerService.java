package it.polimi.db2.db2project.ejbmodule.services;

import it.polimi.db2.db2project.ejbmodule.entities.*;
import it.polimi.db2.db2project.ejbmodule.exceptions.WrongCredentialsException;

import javax.ejb.Stateless;
import javax.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Stateless
public class CustomerService {
    @PersistenceContext(unitName = "TelcoApplicationEJB")
    private EntityManager em;

    public void newOrder(User user, TelcoPackage telcoPackage, ValidityPeriod validityPeriod, LocalDate date, LocalDate startDate, boolean orderStatus, int amount, List<OptionalProduct> optionals){
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setUser(user);
        customerOrder.setTelcoPackage(telcoPackage);
        customerOrder.setValidityPeriod(validityPeriod);
        customerOrder.setDate(date);
        customerOrder.setStartDate(startDate);
        customerOrder.setOrderStatus(orderStatus);
        customerOrder.setAmount(amount);
        customerOrder.setOptionalProducts(optionals);
        em.persist(customerOrder);
    }
}
