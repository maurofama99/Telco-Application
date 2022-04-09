package it.polimi.db2.db2project.ejbmodule.services;

import it.polimi.db2.db2project.ejbmodule.entities.*;
import it.polimi.db2.db2project.ejbmodule.exceptions.WrongCredentialsException;

import javax.ejb.Stateless;
import javax.persistence.*;

import java.util.List;

@Stateless
public class PackageService {
    @PersistenceContext(unitName = "TelcoApplicationEJB")
    private EntityManager em;

    public List<TelcoPackage> getPackages(){
        List<TelcoPackage> telcoPackages = null;
        telcoPackages = em.createNamedQuery("TelcoPackage.getPackages", TelcoPackage.class).getResultList();
        return telcoPackages;
    }

    public TelcoPackage findPackageByID(Integer id){
        TelcoPackage telcoPackage = em.find(TelcoPackage.class, id);
        return telcoPackage;
    }

    public void createPackage(String name, List<Service> services, List<OptionalProduct> optionals, List<ValidityPeriod> vperiods) {
        TelcoPackage telcoPackage = new TelcoPackage();
        telcoPackage.setName(name);
        telcoPackage.setServices(services);
        telcoPackage.setValidityPeriods(vperiods);
        if (optionals != null) telcoPackage.setOptionalProducts(optionals);
        em.persist(telcoPackage);
    }
}
