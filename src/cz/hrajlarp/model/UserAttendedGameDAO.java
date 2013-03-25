package cz.hrajlarp.model;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Prasek
 * Date: 24.3.13
 * Time: 13:52
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UserAttendedGameDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly=true)
    public void getAllObjects(){
        final Session session = sessionFactory.openSession();
        try {
            System.out.println("querying all the managed entities...");
            final Map metadataMap = sessionFactory.getAllClassMetadata();
            for (Object key : metadataMap.keySet()) {
                final ClassMetadata classMetadata = (ClassMetadata) metadataMap.get(key);
                final String entityName = classMetadata.getEntityName();
                final Query query = session.createQuery("from " + entityName);
                System.out.println("executing: " + query.getQueryString());
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
            }
        } finally {
            session.close();
        }
    }

    /**
     * This method adds new userAttedndedGame record into database
     * @param record
     */
    @Transactional(readOnly=false)
    public void addUserAttendedGame(UserAttendedGameEntity record){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(record);
        session.getTransaction().commit();
        session.close();
    }

    @Transactional(readOnly=false)
    public void deleteUserAttendedGame(UserAttendedGameEntity record) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(record);
        session.getTransaction().commit();
        session.close();
    }

    @Transactional(readOnly=false)
    public boolean isLogged(UserAttendedGameEntity uage) {
        if(uage.getGameId() <= 0 || uage.getUserId() <= 0) return true;

        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from UserAttendedGameEntity where game_id= :gameId and user_id= :userId ");
            query.setParameter("gameId", uage.getGameId());
            query.setParameter("userId", uage.getUserId());
            List list = query.list();
            return (list != null && !list.isEmpty())?true:false;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return true;
    }

    @Transactional(readOnly=false)
    public boolean isSubstitute(UserAttendedGameEntity uage) {
        if(uage.getGameId() <= 0 || uage.getUserId() <= 0) return true;

        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from UserAttendedGameEntity where game_id= :gameId and user_id= :userId and substitute = true ");
            query.setParameter("gameId", uage.getGameId());
            query.setParameter("userId", uage.getUserId());
            List list = query.list();
            return (list != null && !list.isEmpty())?true:false;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return true;
    }

    @Transactional(readOnly = true)
    public List getUsersByGameId(int gameId){

        if(gameId <= 0) return null;

        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("select distinct uag.userAttended from UserAttendedGameEntity uag where uag.gameId in (:gameId)");
            query.setParameter("gameId", gameId);
            List list = query.list();

            return list;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return null;
    }
}
