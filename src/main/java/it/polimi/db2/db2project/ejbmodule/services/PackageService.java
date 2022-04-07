package it.polimi.db2.db2project.ejbmodule.services;

import it.polimi.db2.db2project.ejbmodule.entities.Service;
import it.polimi.db2.db2project.ejbmodule.entities.TelcoPackage;
import it.polimi.db2.db2project.ejbmodule.entities.User;
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
}
