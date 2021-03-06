package cz.hrajlarp.model.dao;

import cz.hrajlarp.model.entity.GameEntity;
import cz.hrajlarp.model.entity.UserAttendedGameEntity;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Prasek
 * Date: 12.3.13
 * Time: 14:45
 * To change this template use File | Settings | File Templates.
 */
@Component
public class GameDAO {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Methods gets Game fro database by given id number
     * @param gameId id number of the game
     * @return game with given id
     */
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
        return listGames(false, false, null);
    }

    /**
     * @return List of all games in future from current date
     */
    public List<GameEntity> getFutureGames(){
        return listGames(true, true, null);
    }

    /**
     * @return List of all games in past by current date
     */
    public List<GameEntity> getFormerGames(Integer limit){
        return listGames(true, false, limit);
    }


    /**
     * Method gets all games in database which fits given criteria
     * @param criteria true if some criteria is considered
     * @param future true if listed only future games, false if former games
     * @param limit It is possible to limit amount of obtained games.
     * @return list of all games based on given criteria
     */
    private List<GameEntity> listGames(boolean criteria, boolean future, Integer limit){

        Session session = sessionFactory.openSession();
        try{
            StringBuilder query = new StringBuilder("from GameEntity ");

            if(!criteria); /* no criteria for date (list all games) */
            else if(future) // future games
                query.append(" where date >= current_timestamp");
            else   // former games
                query.append(" where date < current_timestamp");

            query.append(" order by date");
            if(!future){
                query.append(" desc");
            }
            if(limit != null) {
                query.append(" limit " + limit);
            }
            System.out.println("executing: " + query.toString());

            Query finalQuery = session.createQuery(query.toString());
            return finalQuery.list();
        }
        finally { session.close(); }
    }

    public List<GameEntity> GetGamesMonthInFuture(){
        Session session = sessionFactory.openSession();
        try{
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, 1);
            Timestamp date = new Timestamp(cal.getTimeInMillis());
            Timestamp from = new Timestamp(new Date().getTime());
            Query finalQuery = session.createQuery("from GameEntity where date >= :fromDate and date <= :date and shortText=:shortText order by date");
            finalQuery.setTimestamp("date", date);
            finalQuery.setTimestamp("fromDate", from);
            finalQuery.setString("shortText", "Festivalová");
            return finalQuery.list();
        }
        finally {
            session.close();
        }
    }

    public List<GameEntity> GetGamesTwoWeeksInFuture(){
        Session session = sessionFactory.openSession();
        try{
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.WEEK_OF_MONTH, 2);
            Timestamp date = new Timestamp(cal.getTimeInMillis());
            Timestamp from = new Timestamp(new Date().getTime());
            Query finalQuery = session.createQuery("from GameEntity where date >= :fromDate and date <= :date and shortText=:shortText order by date");
            finalQuery.setTimestamp("fromDate", from);
            finalQuery.setTimestamp("date", date);
            finalQuery.setString("shortText", "Festivalová");
            return finalQuery.list();
        }
        finally {
            session.close();
        }
    }

    public List<GameEntity> getTwoWeeksPast() {
        Session session = sessionFactory.openSession();
        try{
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.WEEK_OF_MONTH, -2);
            Timestamp date = new Timestamp(cal.getTimeInMillis());
            Query finalQuery = session.createQuery("from GameEntity where date <= :date and shortText=:shortText order by date");
            finalQuery.setTimestamp("date", date);
            finalQuery.setString("shortText", "Festivalová");
            return finalQuery.list();
        }
        finally {
            session.close();
        }
    }

    /**
     * This method adds new game record into database
     * @param game
     */
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
    public void editGame(GameEntity game){
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.update(game);
            session.getTransaction().commit();
        }
        finally { session.close(); }
    }

    /**
     * This method returns true if user with userId created game with gameId.
     * @param gameId
     * @param userId
     * @return
     */
    public boolean userOwnsGame(int gameId, int userId) {

        if(userId <= 0) return false;

        Session session = sessionFactory.openSession();
        try{
            Query query = session.createQuery("from GameEntity where id = :id and addedBy= :user ");
            query.setParameter("id", gameId);
            query.setParameter("user", userId);
            return (query.uniqueResult() != null);
        }
        finally { session.close(); }
    }

    public List<GameEntity> getInvalidGames() {
        Session session = sessionFactory.openSession();
        try{
            Query query = session.createQuery("from GameEntity where confirmed = false ");
            return query.list();
        }
        finally { session.close(); }
    }

    /**
     * Delete method for GameEntity table.
     * @param record object for delete.
     */
    public void deleteGame(GameEntity record) {
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.delete(record);
            session.getTransaction().commit();
        }
        finally { session.close(); }
    }

    public List<String> getAllActions() {
        Session session = sessionFactory.openSession();
        try{
            Query query = session.createQuery("Select distinct action from GameEntity");
            return query.list();
        }
        finally { session.close(); }
    }

    public List<GameEntity> getGamesWithAction(String actionName) {
        Session session = sessionFactory.openSession();
        try{
            Query finalQuery = session.createQuery("from GameEntity where action = :action order by date");
            finalQuery.setString("action", actionName);
            return finalQuery.list();
        }
        finally {
            session.close();
        }
    }

    public List<GameEntity> attendedOnActionByPlayer(Integer playerId, String action) {
        Session session = sessionFactory.openSession();
        try{
            SQLQuery query = session.createSQLQuery("SELECT *" +
                    "  FROM game AS gm INNER JOIN user_attended_game AS uag ON uag.game_id = gm.id" +
                    "  INNER JOIN hraj_user AS usr on usr.id = uag.user_id WHERE usr.id = :playerId AND gm.action = :action");
            query.addEntity(GameEntity.class);
            query.setInteger("playerId", playerId);
            query.setString("action", action);

            return query.list();
        }
        finally {
            session.close();
        }
    }
}