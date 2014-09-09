package cz.hrajlarp.model.dao;

import java.util.List;

import cz.hrajlarp.model.entity.GameEntity;
import cz.hrajlarp.model.entity.HrajUserEntity;
import cz.hrajlarp.model.entity.PreRegNotificationEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class PreRegNotificationDAO {

    @Autowired
    private SessionFactory sessionFactory;
    
    public List<PreRegNotificationEntity> getAllPreRegNotifications(){
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("from PreRegNotificationEntity");
            return query.list();
        }
        finally { session.close(); }
    }

    public boolean isSubscribedForPreReg(HrajUserEntity user, GameEntity game){
        if(user == null || game == null) return false;
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("FROM PreRegNotificationEntity WHERE userId = :userId AND gameId = :gameId");
            query.setParameter("userId", user.getId());
            query.setParameter("gameId", game.getId());
            return !query.list().isEmpty();
        }
        finally { session.close(); }
    }
    
    /**
     * @param preRegNotification object to add into db.
     */
    public void addPreRegNotification(PreRegNotificationEntity preRegNotification){
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(preRegNotification);
            session.getTransaction().commit();
        }
        finally { session.close(); }
    }

    /**
     * @param preRegNotification object for delete from db.
     */
    public void deletePreRegNotification(PreRegNotificationEntity preRegNotification) {
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.delete(preRegNotification);
            session.getTransaction().commit();
        }
        finally { session.close(); }
    }
    
}