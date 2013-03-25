package cz.hrajlarp.model;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
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
            Query query = session.createQuery("from HrajUserEntity where id= :id ");
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

    /**
     * Adds new user into DB.
     */
    @Transactional
    public void addUser(HrajUserEntity user){
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();

        session.close();
    }

    /**
     * Updates user data in DB.
     */
    @Transactional
    public void editUser(HrajUserEntity user){
        Session session = sessionFactory.openSession();

        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();

        session.close();
    }
}