package it.polimi.db2.db2project.ejbmodule.services;

import it.polimi.db2.db2project.ejbmodule.entities.Alert;
import it.polimi.db2.db2project.ejbmodule.entities.MV3;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class SalesReportService {
    @PersistenceContext(unitName = "TelcoApplicationEJB")
    private EntityManager em;

    /*-----MV1-----*/

    // Number of total purchases per package
    public List<Object[]> totPurchPckg(){
        return em.createNamedQuery("mv1.totpurch").getResultList();
    }

    // The number of total purchases per package and validity period.
    public List<Object[]> totPurchPckgVp(){
        return em.createNamedQuery("mv1.totpurchvp").getResultList();
    }

    /*-----MV2-----*/

    // The total value of sales per package with the optional products.
    public List<Object[]> totValueOp(){
        return em.createNamedQuery("mv2.totvalueop").getResultList();
    }

    // The total value of sales per package without the optional products.
    public List<Object[]> totValueNoop(){
        return em.createNamedQuery("mv2.totvaluenoop").getResultList();
    }

    // The average number of optional products sold together with each service package.
    public List<Object[]> optionalsAvg(){
        return em.createNamedQuery("mv2.opperpackage").getResultList();
    }

    /*-----MV3-----*/

    // Best seller optional product
    public List<Object[]> bestSellerOp(){
        return (List<Object[]>) em.createNamedQuery("mv3.bestseller").getResultList();
    }


    /* -----Alerts-----*/
    public List<Alert> alerts(){
        return  em.createNamedQuery("Alert.everybody").getResultList();
    }

}
