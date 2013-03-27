package cz.hrajlarp.model;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     * Delete method for UserAttendedGame table.
     * @param record object for delete.
     */
    @Transactional(readOnly=false)
    public void deleteUserAttendedGame(UserAttendedGameEntity record) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(record);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * This method return boolean, saying if selected player has record in UserAttendedTable for selected game.
     * It's not important if user is substitute or not.
     * @param uage
     * @return true if record in table exists
     */
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

    /**
     * This method return boolean saying if selected user in selected game is subsitute or not.
     * @param uage
     * @return true if user is substitute
     */
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

    public List<Game> filterAvailableGames(List<Game> games, HrajUserEntity loggedUser) {

        System.out.println("filterAvailableGames method");

        if(games == null || games.isEmpty()) return games;

        Session session = null;
        try {
            session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            List<Game> availableGames = new ArrayList<Game>();
            for (Game game: games){
                Query query = session.createQuery("select distinct uag.userAttended from UserAttendedGameEntity uag where uag.gameId in (:gameId)");
                query.setParameter("gameId", game.getId());
                System.out.println("executing: " + query.getQueryString());
                List users = query.list();
                System.out.println(Arrays.toString(users.toArray()));

                game.setSignedRolesCounts(users); // fills game info: counts of signed users

                if(game.isAvailableToUser(loggedUser)){
                    System.out.println("game is available: " + game.getName());
                    availableGames.add(game);
                }
                else
                    System.out.println("game is NOT available: " + game.getName() + ", gender:" + loggedUser.getGender());
            }

            transaction.commit();
            return availableGames;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return games;
    }

    public List<UserAttendedGameEntity> getAttendedFormer(HrajUserEntity user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("select userAttendedGame from UserAttendedGameEntity as userAttendedGame " +
                "join userAttendedGame.attendedGame as game " +
                "with game.date < current_timestamp ");
        List<UserAttendedGameEntity> result = query.list();
        transaction.commit();
        session.close();
        return result;
    }

    public List<UserAttendedGameEntity> getAttendedFuture(HrajUserEntity user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("select userAttendedGame from UserAttendedGameEntity as userAttendedGame " +
                "join userAttendedGame.attendedGame as game " +
                "with game.date >= current_timestamp ");
        List<UserAttendedGameEntity> result = query.list();
        transaction.commit();
        session.close();
        return result;
    }
}
