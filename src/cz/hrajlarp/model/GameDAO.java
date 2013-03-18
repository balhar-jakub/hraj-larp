package cz.hrajlarp.model;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Prasek
 * Date: 12.3.13
 * Time: 14:45
 * To change this template use File | Settings | File Templates.
 */
public class GameDAO {

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
     * This method adds new game record into database
     * @param game
     */
    @Transactional(readOnly=false)
    public void addGame(GameEntity game){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(game);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * This method updates game record in database
     * @param game
     */
    @Transactional(readOnly=false)
    public void editGame(GameEntity game){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(game);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * This method selects game from database
     * @param id
     * @return GameEntity with id
     */
    @Transactional(readOnly=true)
    public GameEntity findGame(int id){

            if (id<=0) return null;
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            Query query = session.createQuery("FROM GameEntity WHERE id = :id");
            query.setParameter("id", id);
            List result = query.list();
            session.getTransaction().commit();
            session.close();
            if (result != null && !result.isEmpty()) {
                GameEntity game = (GameEntity) result.get(0);
                return game;
            }
            else return null;

    }

}
