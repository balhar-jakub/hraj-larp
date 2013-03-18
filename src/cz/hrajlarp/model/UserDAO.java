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
 * Date: 9.3.13
 * Time: 19:40
 * To change this template use File | Settings | File Templates.
 */
public class UserDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly=true)
    public HrajUserEntity getUserById(int userId){

        if(userId <= 0) return null;

        Session session = null;
        try {
            session = sessionFactory.openSession();
            Query query = session.createQuery("from UserEntity where id= :id ");
            query.setParameter("id", userId);
            System.out.println("executing: " + query.getQueryString());
            List list = query.list();
            System.out.println(list);
            return (list != null && !list.isEmpty())?(HrajUserEntity)list.get(0):null;
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
}
