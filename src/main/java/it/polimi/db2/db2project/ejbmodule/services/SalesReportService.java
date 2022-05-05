package it.polimi.db2.db2project.ejbmodule.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SalesReportService {
    @PersistenceContext(unitName = "TelcoApplicationEJB")
    private EntityManager em;

}
