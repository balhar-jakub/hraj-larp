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
        try {
            session.beginTransaction();
            session.save(record);
            session.getTransaction().commit();
        }
        finally { session.close(); }
    }

    /**
     * Delete method for UserAttendedGame table.
     * @param record object for delete.
     */
    @Transactional(readOnly=false)
    public void deleteUserAttendedGame(UserAttendedGameEntity record) {
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.delete(record);
            session.getTransaction().commit();
        }
        finally { session.close(); }
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

        Session session = sessionFactory.openSession();
        try{
            Query query = session.createQuery("from UserAttendedGameEntity where game_id= :gameId and user_id= :userId ");
            query.setParameter("gameId", uage.getGameId());
            query.setParameter("userId", uage.getUserId());
            UserAttendedGameEntity entity = (UserAttendedGameEntity) query.uniqueResult();
            return (entity != null);
        }
        finally { session.close(); }
    }

    /**
     * This method return boolean saying if selected user in selected game is subsitute or not.
     * @param uage
     * @return true if user is substitute
     */
    @Transactional(readOnly=false)
    public boolean isSubstitute(UserAttendedGameEntity uage) {
        if(uage.getGameId() <= 0 || uage.getUserId() <= 0) return true;

        Session session = sessionFactory.openSession();
        try{
            Query query = session.createQuery("from UserAttendedGameEntity where game_id= :gameId and user_id= :userId and substitute = true ");
            query.setParameter("gameId", uage.getGameId());
            query.setParameter("userId", uage.getUserId());
            return (query.uniqueResult()!=null)?true:false;
        }
        finally { session.close(); }
    }

    @Transactional(readOnly = true)
    public List getUsersByGameId(int gameId){

        if(gameId <= 0) return null;

        Session session = sessionFactory.openSession();
        try{
            Query query = session.createQuery("select distinct uag.userAttended from UserAttendedGameEntity uag where uag.gameId in (:gameId)");
            query.setParameter("gameId", gameId);
            return query.list();
        }
        finally { session.close(); }
    }

    public List<GameEntity> filterAvailableGames(List<GameEntity> games, HrajUserEntity loggedUser) {

        if(games == null || games.isEmpty()) return games;

        Session session = sessionFactory.openSession();
        try{
            Transaction transaction = session.beginTransaction();
            List<GameEntity> availableGames = new ArrayList<GameEntity>();
            for (GameEntity game : games) {
                Query query = session.createQuery("select distinct uag.userAttended from UserAttendedGameEntity uag where uag.gameId in (:gameId)");
                query.setParameter("gameId", game.getId());
                List users = query.list();

                game.setAssignedUsers(users); // fills game info: counts of signed users

                if (game.isAvailableToUser(loggedUser)) {
                    availableGames.add(game);
                }
            }
            transaction.commit();
            return availableGames;
        }
        finally { session.close(); }
    }

    public List<UserAttendedGameEntity> getAttendedFormer(HrajUserEntity user) {
        Session session = sessionFactory.openSession();
        try{
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("select userAttendedGame from UserAttendedGameEntity as userAttendedGame " +
                    "join userAttendedGame.attendedGame as game " +
                    "with game.date < current_timestamp ");
            List<UserAttendedGameEntity> result = query.list();
            transaction.commit();
            return result;
        }
        finally { session.close(); }
    }

    public List<UserAttendedGameEntity> getAttendedFuture(HrajUserEntity user) {
        Session session = sessionFactory.openSession();
        try{
            Transaction transaction = session.beginTransaction();

            Query query = session.createQuery("select userAttendedGame from UserAttendedGameEntity as userAttendedGame " +
                    "join userAttendedGame.attendedGame as game " +
                    "with game.date >= current_timestamp ");
            List<UserAttendedGameEntity> result = query.list();
            transaction.commit();
            return result;
        }
        finally { session.close(); }
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
     @Transactional(readOnly = true)
     public UserAttendedGameEntity getFirstSubstitute(int gameId, int gender) {

         Session session = sessionFactory.openSession();
         try{
             if (gender < 2) { //because of missing hib.xml mapping is impossible to you inner join on construction. classic sql was used instead
                 /*SQLQuery query = session.createSQLQuery("select * from user_attended_game " +
                     "inner join hraj_user on user_attended_game.user_id = hraj_user.id " +
                     "where user_attended_game.game_id= :gameId and user_attended_game.substitute = true " +
                     "and hraj_user.gender = :gender order by user_attended_game.added asc");
                 query.addEntity(UserAttendedGameEntity.class);*/
                 Query query = session.createQuery("from UserAttendedGameEntity as uag join uag.userAttended as user " +
                         "with user.gender = :gender and uag.game_id = :gameId and uag.substitute = true");
                 query.setParameter("gameId", gameId);
                 query.setParameter("gender", gender);
                 return (UserAttendedGameEntity) query.uniqueResult();
             } else {
                 Query query = session.createQuery("from UserAttendedGameEntity where game_id= :gameId and substitute = true order by added asc");
                 query.setParameter("gameId", gameId);
                 return (UserAttendedGameEntity) query.uniqueResult();
             }
        }
        finally { session.close(); }
     }

    /**
     * Update method for table UserAttendedGame.
     * @param uage new record
     */
    @Transactional(readOnly = false)
    public void editUserAttendedGame(UserAttendedGameEntity uage) {
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.update(uage);
            session.getTransaction().commit();
        }
        finally { session.close(); }
    }
}
