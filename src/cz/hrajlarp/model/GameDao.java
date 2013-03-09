package cz.hrajlarp.model;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly=true)
    public GameEntity getGameById(int gameId){

        if(gameId <= 0) return null;

        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from GameEntity where id= :id ");
            query.setParameter("id", gameId);
            System.out.println("executing: " + query.getQueryString());
            List list = query.list();
            System.out.println(list);
            return (list != null && !list.isEmpty())?(GameEntity)list.get(0):null;
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

//    public boolean addGame(GameEntity game){
//        Session session = null;
//        try{
//            session = sessionFactory.openSession();
//            session.save(game);
//            return true;
//        }
//        catch(Exception e){
//            e.printStackTrace();
//            return false;
//        }
//        finally{
//            if (session != null && session.isOpen()) {
//                session.close();
//            }
//        }
//    }
}
