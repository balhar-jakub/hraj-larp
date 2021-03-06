package cz.hrajlarp.model.dao;

import cz.hrajlarp.model.entity.GameEntity;
import cz.hrajlarp.model.entity.HrajUserEntity;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
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

    /**
     * Gets user from database by his id number
     *
     * @param userId user identifier
     * @return user with given id
     */
    public HrajUserEntity getUserById(int userId) {

        if (userId <= 0) return null;

        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("from HrajUserEntity where id= :id ");
            query.setParameter("id", userId);
            System.out.println("executing: " + query.getQueryString());
            return (HrajUserEntity) query.uniqueResult();
        } finally {
            session.close();
        }
    }

    /**
     * Gets user from database by his user name (login name)
     *
     * @param login user name
     * @return user with given user name
     */
    public HrajUserEntity getUserByLogin(String login) {

        if (login == null || login.isEmpty()) return null;

        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("from HrajUserEntity where user_name= :login ");
            query.setParameter("login", login);
            System.out.println("executing: " + query.getQueryString());
            return (HrajUserEntity) query.uniqueResult();
        } finally {
            session.close();
        }
    }

    /**
     * Adds new user into DB.
     */
    public void addUser(HrajUserEntity user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    /**
     * Updates user data in DB.
     */
    public void editUser(HrajUserEntity user) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } finally {
            session.close();
        }
    }

    /**
     * Method checks if given username is unique (not present in the database yet).
     *
     * @param userName tested user name
     * @return true if name is not present in the database yet
     */
    public boolean userNameIsUnique(String userName) {
        if (userName == null || userName.isEmpty()) return false;

        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("from HrajUserEntity where user_name= :login ");
            query.setParameter("login", userName);
            return (query.uniqueResult() == null);
        } finally {
            session.close();
        }
    }

    public HrajUserEntity getUserToActivate(String activationLink) {
        if (activationLink == null || activationLink.isEmpty()) return null;

        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("from HrajUserEntity where activation_link= :activationLink ");
            query.setParameter("activationLink", activationLink);
            System.out.println("executing: " + query.getQueryString());
            return (HrajUserEntity) query.uniqueResult();
        } finally {
            session.close();
        }
    }

    /**
     * @return list of all users stored in the DB
     */
    public List<HrajUserEntity> listUsers() {
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("from HrajUserEntity");
            return query.list();
        } finally {
            session.close();
        }
    }

    public List<HrajUserEntity> getEveryoneAttendingAction(String action) {
        Session session = sessionFactory.openSession();
        try {
            SQLQuery query = session.createSQLQuery("SELECT distinct on (usr.id) usr.id," +
                    "  usr.activated, usr.activation_link, usr.email, usr.gender, usr.last_name, usr.mail_information, usr.name, usr.password, usr.phone, usr.user_name" +
                    "  FROM hraj_user AS usr INNER JOIN user_attended_game AS uag ON usr.id = uag.user_id" +
                    "  INNER JOIN game AS gm ON gm.id = uag.game_id where gm.action = :action");
            query.addEntity(HrajUserEntity.class);
            query.setString("action", action);

            return query.list();
        } finally {
            session.close();
        }
    }
}