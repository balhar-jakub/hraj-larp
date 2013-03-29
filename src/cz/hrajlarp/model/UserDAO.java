package cz.hrajlarp.model;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

        Session session = sessionFactory.openSession();
        try{
            Query query = session.createQuery("from HrajUserEntity where id= :id ");
            query.setParameter("id", userId);
            System.out.println("executing: " + query.getQueryString());
            return (HrajUserEntity) query.uniqueResult();
        }
        finally { session.close(); }
    }

    @Transactional(readOnly=true)
    public HrajUserEntity getUserByLogin(String login){

        if(login == null || login.isEmpty()) return null;

        Session session = sessionFactory.openSession();
        try{
            Query query = session.createQuery("from HrajUserEntity where user_name= :login ");
            query.setParameter("login", login);
            System.out.println("executing: " + query.getQueryString());
            return (HrajUserEntity) query.uniqueResult();
        }
        finally { session.close(); }
    }

    /**
     * Adds new user into DB.
     */
    @Transactional
    public void addUser(HrajUserEntity user){
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
        finally { session.close(); }
    }

    /**
     * Updates user data in DB.
     */
    @Transactional
    public void editUser(HrajUserEntity user){
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
        finally { session.close(); }
    }
}