package cz.hrajlarp.model;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
* Created by IntelliJ IDEA.
* User: Matheo
* Date: 22.3.13
* Time: 11:34
* To change this template use File | Settings | File Templates.
*/
public class UserAttendedGameDAO {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public boolean isSubstitute(int gameId, int userId){

        if(gameId <= 0 || userId <= 0) return false;

        Session session = null;
        try {
            session = sessionFactory.openSession();

            StringBuilder q = new StringBuilder("from UserAttendedGameEntity");
            q.append(" where primaryKey.gameId= :gId");
            q.append(" and primaryKey.userId = :uId");
            System.out.println(q);

            Query query = session.createQuery(q.toString());
            query.setParameter("gId", gameId);
            query.setParameter("uId", userId);
            System.out.println("executing: " + query.getQueryString());
            List list = query.list();
            System.out.println(Arrays.toString(list.toArray()));

            return ((UserAttendedGameEntity) list.get(0)).isSubstitute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return false;
    }


    @Transactional(readOnly = true)
    public List getUsersByGameId(int gameId){

        if(gameId <= 0) return null;

        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("select distinct uag.userAttended from UserAttendedGameEntity uag where uag.gameId in (:gameId)");
            query.setParameter("gameId", gameId);

            System.out.println("executing: " + query.getQueryString());
            List list = query.list();
            System.out.println(Arrays.toString(list.toArray()));

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
}
