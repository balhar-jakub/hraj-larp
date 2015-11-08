package cz.hrajlarp.model.dao;

import cz.hrajlarp.model.entity.GameEntity;
import cz.hrajlarp.model.entity.HrajUserEntity;
import cz.hrajlarp.model.entity.UserAttendedGameEntity;
import org.hibernate.*;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public void getAllObjects() {
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
     * This method adds new UserAttendedGame record into database
     *
     * @param record
     */
    public void addUserAttendedGame(UserAttendedGameEntity record) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(record);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    /**
     * Delete method for UserAttendedGame table.
     *
     * @param record object for delete.
     */
    public void deleteUserAttendedGame(UserAttendedGameEntity record) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(record);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public boolean isLogged(int gameId, int userId) {
        UserAttendedGameEntity uage = new UserAttendedGameEntity();
        uage.setGameId(gameId);
        uage.setUserId(userId);
        return isLogged(uage);
    }

    public boolean isSubstitute(int gameId, int userId) {
        UserAttendedGameEntity uage = new UserAttendedGameEntity();
        uage.setGameId(gameId);
        uage.setUserId(userId);
        return isSubstitute(uage);
    }

    /**
     * This method return boolean, saying if selected player has record in UserAttendedTable for selected game.
     * It's not important if user is substitute or not.
     *
     * @param uage
     * @return true if record in table exists
     */
    public boolean isLogged(UserAttendedGameEntity uage) {
        if (uage.getGameId() <= 0 || uage.getUserId() <= 0) return true;

        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("from UserAttendedGameEntity where gameId= :gameId and userId= :userId ");
            query.setParameter("gameId", uage.getGameId());
            query.setParameter("userId", uage.getUserId());
            UserAttendedGameEntity entity = (UserAttendedGameEntity) query.uniqueResult();
            return (entity != null);
        } finally {
            session.close();
        }
    }

    public UserAttendedGameEntity getLogged(Integer gameId, Integer userId) {
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("from UserAttendedGameEntity where gameId= :gameId and userId= :userId ");
            query.setParameter("gameId", gameId);
            query.setParameter("userId", userId);
            UserAttendedGameEntity entity = (UserAttendedGameEntity) query.uniqueResult();
            return entity;
        } finally {
            session.close();
        }
    }

    /**
     * This method return boolean saying if selected user in selected game is subsitute or not.
     *
     * @param uage
     * @return true if user is substitute
     */
    public boolean isSubstitute(UserAttendedGameEntity uage) {
        if (uage.getGameId() <= 0 || uage.getUserId() <= 0) return true;

        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("from UserAttendedGameEntity where gameId= :gameId and userId= :userId and substitute = true ");
            query.setParameter("gameId", uage.getGameId());
            query.setParameter("userId", uage.getUserId());
            return (query.uniqueResult() != null);
        } finally {
            session.close();
        }
    }

    /**
     * This method return boolean saying if selected user in selected game is subsitute or not.
     *
     * @param uage
     * @return true if user is substitute
     */
    public boolean isRegular(UserAttendedGameEntity uage) {
        if (uage.getGameId() <= 0 || uage.getUserId() <= 0) return true;

        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("from UserAttendedGameEntity where gameId= :gameId and userId= :userId and substitute = false ");
            query.setParameter("gameId", uage.getGameId());
            query.setParameter("userId", uage.getUserId());
            return (query.uniqueResult() != null);
        } finally {
            session.close();
        }
    }

    /**
     * Method gets all users (including substitutes) signed up for game with given id.
     *
     * @param gameId game identifier
     * @return list of users
     */
    public List<UserAttendedGameEntity> getAllPlayersOfGame(int gameId) {

        if (gameId <= 0) return null;

        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("from UserAttendedGameEntity where gameId in (:gameId) order by added desc");
            query.setParameter("gameId", gameId);
            return query.list();
        } finally {
            session.close();
        }
    }

    /**
     * Method gets from database all users signed up for game with given id.
     * Excludes users signed as substitutes.
     *
     * @param gameId game identifier
     * @return list of users signed up for game as regular users (not substitutes)
     */
    public List getUsersByGameIdNoSubstitutes(int gameId) {

        if (gameId <= 0) return null;

        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("select distinct uag.userAttended from UserAttendedGameEntity uag" +
                    " where uag.gameId in (:gameId) and uag.substitute = false");
            query.setParameter("gameId", gameId);
            return query.list();
        } finally {
            session.close();
        }
    }

    public List<UserAttendedGameEntity> getPlayers(int gameId, boolean substitute) {
        if (gameId <= 0) return null;

        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("from UserAttendedGameEntity where gameId in (:gameId) " +
                    "and substitute = :substitute");
            query.setParameter("gameId", gameId);
            query.setParameter("substitute", substitute);
            return query.list();
        } finally {
            session.close();
        }
    }

    /**
     * Method gets from database all users signed up
     * as substitutes for game with given id.
     * Excludes users signed as regular (not substitutes).
     *
     * @param gameId game identifier
     * @return list of users signed up for game as substitutes (not regular users)
     */
    public List getSubstituteUsersByGameId(int gameId) {

        if (gameId <= 0) return null;

        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("select distinct uag.userAttended from UserAttendedGameEntity uag" +
                    " where uag.gameId in (:gameId) and uag.substitute = true");
            query.setParameter("gameId", gameId);
            return query.list();
        } finally {
            session.close();
        }
    }

    public Long getNextVariableSymbol() {
        Session session = sessionFactory.openSession();
        Query query = session.createSQLQuery("select nextval('hraj_user_attended_game_seq')");
        Long key = ((BigInteger) query.uniqueResult()).longValue();
        return key;
    }

    /**
     * Gets games available to user from given list.
     * Excludes games, where user is already signed in.
     * Excludes games, where capacity is filled (considering user gender).
     * Does not consider date of game.
     *
     * @param games      initial list, supposed to be future games
     * @param loggedUser user who wants to be logged on game
     * @return filtered list of games
     */
    public List<GameEntity> filterAvailableGames(List<GameEntity> games, HrajUserEntity loggedUser) {

        if (games == null || games.isEmpty()) return games;

        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();
            List<GameEntity> availableGames = new ArrayList<GameEntity>();
            for (GameEntity game : games) {
                try {
                    game.countPlayers(this); // fills game info: counts of signed users
                } catch (Exception e) {
                    e.printStackTrace();
                    /* TODO handle error and fix data in the database */
                }

                if (game.isAvailableToUser(this, loggedUser)) {
                    availableGames.add(game);
                }
            }
            transaction.commit();
            return availableGames;
        } finally {
            session.close();
        }
    }

    /**
     * Method gets UserAttendedGamesEntity entities,
     * where user was signed for the past events.
     *
     * @param user
     * @return list of UserAttendedGamesEntity entities
     */
    public List<UserAttendedGameEntity> getAttendedFormer(HrajUserEntity user) {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery(
                    "select userAttendedGame from UserAttendedGameEntity as userAttendedGame " +
                            " join userAttendedGame.attendedGame as game " +
                            " with game.date < current_timestamp" +
                            " join userAttendedGame.userAttended as user " +
                            " with user.id = :id");
            query.setParameter("id", user.getId());
            List<UserAttendedGameEntity> result = query.list();
            transaction.commit();
            return result;
        } finally {
            session.close();
        }
    }

    /**
     * Method gets UserAttendedGamesEntity entities,
     * where user is signed for the future events.
     *
     * @param user
     * @return list of UserAttendedGamesEntity entities
     */
    public List<UserAttendedGameEntity> getAttendedFuture(HrajUserEntity user) {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery(
                    "select userAttendedGame from UserAttendedGameEntity as userAttendedGame " +
                            " join userAttendedGame.attendedGame as game " +
                            " with game.date >= current_timestamp" +
                            " join userAttendedGame.userAttended as user " +
                            " with user.id = :id");
            query.setParameter("id", user.getId());
            List<UserAttendedGameEntity> result = query.list();
            transaction.commit();
            return result;
        } finally {
            session.close();
        }
    }

    /**
     * This method return first substitute player according to game and gender.
     * If there are free roles for men (gender = 0) or women (gender = 1), its created a sql query with inner join to HrajUser table. Query returns UserAttendedGame record where user's gender equals parameter.
     * If there are only free roles for both genders (gender = 2), it's chosen substitute player with any gender.
     * All results are ordered by attribute added and only the first one is returned.
     *
     * @param gameId id of selected game
     * @param gender required player's gender
     * @return first substitute player
     */
    public UserAttendedGameEntity getFirstSubstitutedUAG(int gameId, int gender) {

        Session session = sessionFactory.openSession();
        try {
            if (gender < 2) { //hibenate has problems with "join on" construction, classic sql was used instead
                SQLQuery query = session.createSQLQuery("select * from user_attended_game as uag inner join hraj_user on uag.user_id = hraj_user.id " +
                        "where uag.game_id= :gameId and uag.substitute = true and hraj_user.gender = :gender order by uag.added asc");
                query.addEntity(UserAttendedGameEntity.class);
                query.setInteger("gender", gender);
                query.setInteger("gameId", gameId);
                if (query.list() != null && !query.list().isEmpty())
                    return (UserAttendedGameEntity) query.list().get(0);
                else return null;
            } else {
                Query query = session.createQuery("from UserAttendedGameEntity where gameId= :gameId and substitute = true order by added asc");
                query.setParameter("gameId", gameId);
                if (query.list() != null && !query.list().isEmpty())
                    return (UserAttendedGameEntity) query.list().get(0);
                else return null;
            }
        } finally {
            session.close();
        }
    }

    /**
     * Update method for table UserAttendedGame.
     *
     * @param uage new record
     */
    public void editUserAttendedGame(UserAttendedGameEntity uage) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(uage);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    public List<UserAttendedGameEntity> getAllFuture() {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery(
                    "select userAttendedGame from UserAttendedGameEntity as userAttendedGame " +
                            " join userAttendedGame.attendedGame as game " +
                            " with date(game.date) - date(current_date()) = 2");
            List<UserAttendedGameEntity> result = query.list();
            transaction.commit();
            return result;
        } finally {
            session.close();
        }
    }

    public UserAttendedGameEntity getByVS(String vs) {
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery(
                    "from UserAttendedGameEntity where variableSymbol = :varSymbol");
            query.setParameter("varSymbol", vs);
            UserAttendedGameEntity result = (UserAttendedGameEntity) query.uniqueResult();
            transaction.commit();
            return result;
        } finally {
            session.close();
        }
    }

    /**
     * Finds all records where game id is id.
     *
     * @param id game id
     * @return list of all records where game id is id.
     */
    public List<UserAttendedGameEntity> getRecordsByGameId(Integer id) {
        if (id <= 0) return null;

        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("from UserAttendedGameEntity where game_id= :id ");
            query.setParameter("id", id);
            return query.list();
        } finally {
            session.close();
        }
    }

    /**
     * Return all players without payment attending certain action.
     *
     * @param action Name of the action.
     * @return List of all regular players, no substitutes attending games during the action withou payment.
     */
    public List<GamePlayer> actionWithoutPayment(String action) {
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("" +
                            "from UserAttendedGameEntity where substitute = false and (payed = false or payed = null )"
            );
            List<UserAttendedGameEntity> entities = query.list();
            return entities.stream()
                    .filter(userAttendedGameEntity ->
                        userAttendedGameEntity.getAttendedGame().getAction().equalsIgnoreCase(action))
                    .map(userAttendedGameEntity1 -> new GamePlayer(userAttendedGameEntity1, userAttendedGameEntity1.getAttendedGame(), userAttendedGameEntity1.getUserAttended()))
                    .collect(Collectors.toList());
        } finally {
            session.close();
        }
    }

    public class GamePlayer {
        UserAttendedGameEntity player;
        GameEntity game;
        HrajUserEntity user;

        public GamePlayer(UserAttendedGameEntity player, GameEntity game, HrajUserEntity user) {
            this.player = player;
            this.game = game;
            this.user = user;
        }

        public UserAttendedGameEntity getPlayer() {
            return player;
        }

        public GameEntity getGame() {
            return game;
        }

        public HrajUserEntity getUser() {
            return user;
        }
    }
}
