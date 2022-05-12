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

    public void newOrder(User user, TelcoPackage telcoPackage, ValidityPeriod validityPeriod, LocalDate date, LocalDate startDate, boolean orderStatus, int amount, int noopamount, List<OptionalProduct> optionals){
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setUser(user);
        customerOrder.setTelcoPackage(telcoPackage);
        customerOrder.setValidityPeriod(validityPeriod);
        customerOrder.setDate(date);
        customerOrder.setStartDate(startDate);
        customerOrder.setOrderStatus(orderStatus);
        customerOrder.setAmount(amount);
        customerOrder.setNoopAmount(noopamount);
        customerOrder.setOptionalProducts(optionals);
        em.persist(customerOrder);
    }

    public List<CustomerOrder> failedOrder(User user){
        List<CustomerOrder> customerOrders = null;
        customerOrders = em.createNamedQuery("CustomerOrder.failed", CustomerOrder.class).setParameter(1, user.getName()).getResultList();
        return customerOrders;
    }

    public CustomerOrder findOrderbyID(Integer Id) {
        CustomerOrder customerOrder = em.find(CustomerOrder.class, Id);
        return customerOrder;
    }

    //da controllare che abbia senso sta cosa
    public void updateStatus(int Id){
        CustomerOrder customerOrder = em.find(CustomerOrder.class, Id);

        customerOrder.setOrderStatus(true);
        em.merge(customerOrder);

        em.flush();
    }

    public List<CustomerOrder> suspendedOrders (){
        return em.createNamedQuery("CustomerOrder.suspended", CustomerOrder.class).getResultList();
    }
}
