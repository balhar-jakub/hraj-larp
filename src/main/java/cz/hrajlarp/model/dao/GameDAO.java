package cz.hrajlarp.model.dao;

import cz.hrajlarp.model.entity.GameEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
    private EntityManager persistentStore;

    /**
     * Methods gets Game fro database by given id number
     *
     * @param gameId id number of the game
     * @return game with given id
     */
    public GameEntity getGameById(int gameId) {
        assert gameId >= 0;

        Query query = persistentStore.createQuery("from GameEntity where id= :id ");
        query.setParameter("id", gameId);
        return (GameEntity) query.getSingleResult();
    }

    /**
     * @return List of all games in future from current date
     */
    public List<GameEntity> getFutureGames() {
        return listGames(true, true);
    }

    /**
     * @return List of all games in past by current date
     */
    public List<GameEntity> getFormerGames() {
        return listGames(true, false);
    }


    /**
     * Method gets all games in database which fits given criteria
     *
     * @param criteria true if some criteria is considered
     * @param future   true if listed only future games, false if former games
     * @return list of all games based on given criteria
     */
    private List<GameEntity> listGames(boolean criteria, boolean future) {
        StringBuilder query = new StringBuilder("from GameEntity ");

        if (!criteria) ; /* no criteria for date (list all games) */
        else if (future) // future games
            query.append(" where date >= current_timestamp");
        else   // former games
            query.append(" where date < current_timestamp");

        query.append(" order by date");
        if (!future) {
            query.append(" desc");
        }
        System.out.println("executing: " + query.toString());

        Query finalQuery = persistentStore.createQuery(query.toString());
        return finalQuery.getResultList();
    }

    /**
     * This method adds new game record into database
     *
     * @param game
     */
    public void addGame(GameEntity game) {
        persistentStore.persist(game);
    }

    /**
     * This method updates game record in database
     *
     * @param game
     */
    public void editGame(GameEntity game) {
        persistentStore.persist(game);
    }

    public List<GameEntity> getInvalidGames() {
        Query query = persistentStore.createQuery("from GameEntity where confirmed = false ");
        return query.getResultList();
    }

    /**
     * Delete method for GameEntity table.
     *
     * @param record object for delete.
     */
    public void deleteGame(GameEntity record) {
        persistentStore.remove(record);
    }

    public List<String> getAllActions() {
        Query query = persistentStore.createQuery("Select distinct action from GameEntity");
        return query.getResultList();
    }
}