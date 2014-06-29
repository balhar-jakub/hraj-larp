package cz.hrajlarp.dao;

import cz.hrajlarp.api.GenericBuilder;
import cz.hrajlarp.api.GenericHibernateDAO;
import cz.hrajlarp.api.IBuilder;
import cz.hrajlarp.entity.Game;
import cz.hrajlarp.entity.HrajUser;
import cz.hrajlarp.entity.UserAttendedGame;
import cz.hrajlarp.entity.UserGamePK;
import cz.hrajlarp.service.GameService;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@SuppressWarnings({"SimplifiableIfStatement", "unchecked"})
@Repository
public class UserAttendedGameDAO extends GenericHibernateDAO<UserAttendedGame, UserGamePK> {
    @Autowired
    GameService gameService;

    @Override
    public IBuilder getBuilder() {
        return new GenericBuilder<>(UserAttendedGame.class);
    }

    public UserAttendedGame getLogged(Integer gameId, Integer userId){
        return findSingleByCriteria(
                Restrictions.and(
                        Restrictions.eq("userId", userId),
                        Restrictions.eq("gameId", gameId)
                )
        );
    }

    /**
     * Method gets all users (including substitutes) signed up for game with given id.
     * @param gameId game identifier
     * @return list of users
     */
    public List<UserAttendedGame> getAllPlayersOfGame(int gameId){
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from UserAttendedGame where gameId = :gameId order by added desc");
        query.setParameter("gameId", gameId);
        return query.list();
    }

    /**
     * Method gets from database all users signed up for game with given id.
     * Excludes users signed as substitutes.
     * @param gameId game identifier
     * @return list of users signed up for game as regular users (not substitutes)
     */
    public List<UserAttendedGame> getActualPlayersOnly(int gameId){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from UserAttendedGame" +
                " where gameId = :gameId and substitute = false");
        query.setParameter("gameId", gameId);
        return query.list();
    }

    /**
     * Method gets from database all users signed up
     * as substitutes for game with given id.
     * Excludes users signed as regular (not substitutes).
     * @param gameId game identifier
     * @return list of users signed up for game as substitutes (not regular users)
     */
    public List<UserAttendedGame> getSubstitutesByGameId(int gameId){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from UserAttendedGame uag" +
                " where uag.gameId in (:gameId) and uag.substitute = true");
        query.setParameter("gameId", gameId);
        return query.list();
    }

    public Long getNextVariableSymbol(){
        Session session = sessionFactory.openSession();
        Query query = session.createSQLQuery("select nextval('hraj_user_attended_game_seq')");
        return ((BigInteger) query.uniqueResult()).longValue();
    }

    /**
     * Gets games available to user from given list.
     * Excludes games, where user is already signed in.
     * Excludes games, where capacity is filled (considering user gender).
     * Does not consider date of game.
     * @param games initial list, supposed to be future games
     * @param loggedUser user who wants to be logged on game
     * @return filtered list of games
     */
    public List<Game> filterAvailableGames(List<Game> games, HrajUser loggedUser) {
        List<Game> availableGames = new ArrayList<>();
        for (Game game : games) {
            if (gameService.isAvailableToUser(loggedUser, game)) {
                availableGames.add(game);
            }
        }
        return availableGames;
    }

    /**
     * Method gets UserAttendedGamesEntity entities,
     * where user was signed for the past events.
     * @return list of UserAttendedGamesEntity entities
     */
    public List<UserAttendedGame> getAttendedFormer(HrajUser user) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(
                "select userAttendedGame from UserAttendedGame as userAttendedGame " +
                " join userAttendedGame.attendedGame as game " +
                " with game.date < current_timestamp" +
                " join userAttendedGame.userAttended as user " +
                " with user.id = :id");
        query.setParameter("id", user.getId());
        return query.list();
    }

    /**
     * Method gets UserAttendedGamesEntity entities,
     * where user is signed for the future events.
     * @return list of UserAttendedGamesEntity entities
     */
    public List<UserAttendedGame> getAttendedFuture(HrajUser user) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(
                "select userAttendedGame from UserAttendedGame as userAttendedGame " +
                " join userAttendedGame.attendedGame as game " +
                " with game.date >= current_timestamp" +
                " join userAttendedGame.userAttended as user " +
                " with user.id = :id");
        query.setParameter("id", user.getId());
        return query.list();
    }

     /**
     * This method return first substitute player according to game and gender.
     * If there are free roles for men (gender = 0) or women (gender = 1), its created a sql query with inner join to HrajUser table. Query returns UserAttendedGame record where user's gender equals parameter.
     * If there are only free roles for both genders (gender = 2), it's chosen substitute player with any gender.
     * All results are ordered by attribute added and only the first one is returned.
     * @param gameId id of selected game
     * @param gender required player's gender
     * @return first substitute player
     */
     public UserAttendedGame getFirstSubstitutedUAG(int gameId, int gender) {

         Session session = sessionFactory.openSession();
         try{
             if (gender < 2) { //hibenate has problems with "join on" construction, classic sql was used instead
                 SQLQuery query = session.createSQLQuery("select * from user_attended_game as uag inner join hraj_user on uag.user_id = hraj_user.id " +
                     "where uag.game_id= :gameId and uag.substitute = true and hraj_user.gender = :gender order by uag.added asc");
                 query.addEntity(UserAttendedGame.class);
                 query.setInteger("gender", gender);
                 query.setInteger("gameId", gameId);
                 if (query.list()!= null && !query.list().isEmpty()) return (UserAttendedGame) query.list().get(0);
                 else return null;
             } else {
                 Query query = session.createQuery("from UserAttendedGame where gameId= :gameId and substitute = true order by added asc");
                 query.setParameter("gameId", gameId);
                 if (query.list()!= null && !query.list().isEmpty()) return (UserAttendedGame) query.list().get(0);
                 else return null;
             }
        }
        finally { session.close(); }
     }

    public List<UserAttendedGame> getAllFuture() {
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery(
                "select userAttendedGame from UserAttendedGame as userAttendedGame " +
                        " join userAttendedGame.attendedGame as game " +
                        " with date(game.date) - date(current_date()) = 2");
        return query.list();
    }

    public UserAttendedGame getByVS(String vs) {
        Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(
                    "from UserAttendedGame where variableSymbol = :varSymbol");
            query.setParameter("varSymbol", vs);
            return (UserAttendedGame) query.uniqueResult();
    }

    public void delete(int userId, int gameId) {
        UserAttendedGame userAttendedGame = new UserAttendedGame();
        userAttendedGame.setUserId(userId);
        userAttendedGame.setGameId(gameId);
        makeTransient(userAttendedGame);
    }

    public boolean isSignedAsSubstitute(int gameId, int userId) {
        return findSingleByCriteria(Restrictions.and(
                Restrictions.eq("userId", userId),
                Restrictions.eq("gameId", gameId),
                Restrictions.eq("substitute", true)
        )) != null;    }

    public boolean isSignedAsRegular(int gameId, int userId) {
        return findSingleByCriteria(Restrictions.and(
                Restrictions.eq("userId", userId),
                Restrictions.eq("gameId", gameId),
                Restrictions.eq("substitute", false)
        )) != null;
    }

    public UserAttendedGame getSpecificOne(int userId, int gameId) {
        return findById(new UserGamePK(userId, gameId));
    }

    public boolean isAlreadySignedFor(int userId, int gameId) {
        return getSpecificOne(userId, gameId) != null;
    }
}
