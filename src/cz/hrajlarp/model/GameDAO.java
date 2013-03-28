package cz.hrajlarp.model;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Prasek
 * Date: 12.3.13
 * Time: 14:45
 * To change this template use File | Settings | File Templates.
 */
@Component
public class GameDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Methods gets Game fro database by given id number
     * @param gameId id number of the game
     * @return game with given id
     */
    @Transactional(readOnly=true)
    public GameEntity getGameById(int gameId){

        if(gameId <= 0) return null;

        Session session = sessionFactory.openSession();
        try{
            Query query = session.createQuery("from GameEntity where id= :id ");
            query.setParameter("id", gameId);
            return (GameEntity) query.uniqueResult();
        }
        finally { session.close(); }
    }


    /**
     * @return List of all games (former and future too)
     */
    public List<GameEntity> getALLGames(){
        return listGames(false, false);
    }

    /**
     * @return List of all games in future from current date
     */
    public List<GameEntity> getFutureGames(){
        return listGames(true, true);
    }

    /**
     * @return List of all games in past by current date
     */
    public List<GameEntity> getFormerGames(){
        return listGames(true, false);
    }


    /**
     * Method gets all games in database which fits given criteria
     * @param criteria true if some criteria is considered
     * @param future true if listed only future games, false if former games
     * @return list of all games based on given criteria
     */
    @Transactional(readOnly=true)
    private List<GameEntity> listGames(boolean criteria, boolean future){

        Session session = sessionFactory.openSession();
        try{
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            StringBuilder query = new StringBuilder("from GameEntity ");

            if(!criteria); /* no criteria for date (list all games) */
            else if(future) // future games
                query.append(" where date >= current_timestamp");
            else   // former games
                query.append(" where date < current_timestamp");

            query.append(" order by date");
            System.out.println("executing: " + query.toString());

            Query finalQuery = session.createQuery(query.toString());
            return finalQuery.list();
        }
        finally { session.close(); }
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
        }
        finally { session.close(); }
    }

    /**
     * This method adds new game record into database
     * @param game
     */
    @Transactional(readOnly=false)
    public void addGame(GameEntity game){
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.save(game);
            session.getTransaction().commit();
        }
        finally { session.close(); }
    }

    /**
     * This method updates game record in database
     * @param game
     */
    @Transactional(readOnly=false)
    public void editGame(GameEntity game){
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.update(game);
            session.getTransaction().commit();
        }
        finally { session.close(); }
    }
}
