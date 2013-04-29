package cz.hrajlarp.model;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    
    @Transactional(readOnly=true)
    public List<PreRegNotificationEntity> getAllPreRegNotifications(){
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("from PreRegNotificationEntity");
            return query.list();
        }
        finally { session.close(); }
    }

    @Transactional(readOnly=true)
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
    @Transactional(readOnly=false)
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
    @Transactional(readOnly=false)
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