package cz.hrajlarp.model.dao;

import java.util.List;

import cz.hrajlarp.model.entity.GameEntity;
import cz.hrajlarp.model.entity.HrajUserEntity;
import cz.hrajlarp.model.entity.PreRegNotificationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;


@Repository
public class PreRegNotificationDAO {
    private EntityManager persistentStore;

    @Autowired public PreRegNotificationDAO(EntityManager persistentStore) {
        this.persistentStore = persistentStore;
    }

    public List<PreRegNotificationEntity> getAllPreRegNotifications(){
        Query allNotifications = persistentStore.createQuery("from PreRegNotificationEntity");
        return allNotifications.getResultList();
    }

    public boolean isSubscribedForPreReg(HrajUserEntity user, GameEntity game){
        assert user != null && game != null;

        Query query = persistentStore.createQuery("FROM PreRegNotificationEntity WHERE userId = :userId AND gameId = :gameId");
        query.setParameter("userId", user.getId());
        query.setParameter("gameId", game.getId());
        return !query.getResultList().isEmpty();
    }
    
    /**
     * @param preRegNotification object to add into db.
     */
    public void addPreRegNotification(PreRegNotificationEntity preRegNotification){
        persistentStore.persist(preRegNotification);
    }

    /**
     * @param preRegNotification object for delete from db.
     */
    public void deletePreRegNotification(PreRegNotificationEntity preRegNotification) {
        persistentStore.remove(preRegNotification);
    }
    
}