package cz.hrajlarp.model.dao;


import java.util.List;


import cz.hrajlarp.model.entity.AdministratorEntity;
import cz.hrajlarp.model.entity.HrajUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Created by IntelliJ IDEA.
 * User: Matheo
 * Date: 12.4.13
 * Time: 20:07
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AdministratorDAO {
    private EntityManager persistentStore;

    @Autowired public AdministratorDAO(EntityManager persistentStore) {
        this.persistentStore = persistentStore;
    }

    public boolean isAdministrator(HrajUserEntity user){
        return persistentStore.find(AdministratorEntity.class, user.getId()) != null;
    }

    public void setAdministrator(int userId){
        AdministratorEntity newAdministrator = new AdministratorEntity();
        newAdministrator.setId(userId);
        persistentStore.persist(newAdministrator);
    }
    
    public List<Integer> getAdministratorIds(){
        Query allAdministrators = persistentStore.createQuery("select ae.id from AdministratorEntity ae");
        return allAdministrators.getResultList();
    }

    public void removeAdministratorRights(int userId){
        AdministratorEntity entity = new AdministratorEntity();
        entity.setId(userId);
        persistentStore.remove(entity);
    }
}