package cz.hrajlarp.model;

import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Matheo
 * Date: 8.3.13
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */
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
    public Game getGameById(int gameId){

        if(gameId <= 0) return null;

        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from GameEntity where id= :id ");
            query.setParameter("id", gameId);
            List list = query.list();
            return (list != null && !list.isEmpty())?new Game((GameEntity)list.get(0)):null;
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


    /**
     * @return List of all games (former and future too)
     */
    public List<Game> getALLGames(){
        return listGames(false, false);
    }

    /**
     * @return List of all games in future from current date
     */
    public List<Game> getFutureGames(){
        return listGames(true, true);
    }

    /**
     * @return List of all games in past by current date
     */
    public List<Game> getFormerGames(){
        return listGames(true, false);
    }


    /**
     * Method gets all games in database which fits given criteria
     * @param criteria true if some criteria is considered
     * @param future true if listed only future games, false if former games
     * @return list of all games based on given criteria
     */
    @Transactional(readOnly=true)
    private List<Game> listGames(boolean criteria, boolean future){

        Session session = null;
        try {
            session = sessionFactory.openSession();
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            StringBuilder query = new StringBuilder("from GameEntity ");

            if(!criteria){
                /* no criteria for date (list all games) */
            }
            else if(future){ // future games
		        query.append(" where date >= '" + sdf.format(now) + "'");
            }
            else{   // former games
		        query.append(" where date < '" + sdf.format(now) + "'");
            }

		    query.append(" order by date");
            System.out.println("executing: " + query.toString());

            Query finalQuery = session.createQuery(query.toString());
            List list = finalQuery.list();

            if(list == null || list.isEmpty()) return null;

            List<Game> gameList = getGameList(list);
            return (gameList.isEmpty())? null : gameList;
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

    /**
     * Change type of elements in given List to Game if possible
     * @param list List of GameEntity items
     * @return list of Game items created from by given list
     */
    private List<Game> getGameList(List<GameEntity> list){
        List<Game> gameList = new LinkedList<Game>();
        for (GameEntity gen: list)
            try{
                gameList.add(new Game(gen));
            }catch(ClassCastException e){
                e.printStackTrace();
            }
        return gameList;
    }
}