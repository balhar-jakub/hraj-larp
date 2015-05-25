package cz.hrajlarp.model.dao;

import cz.hrajlarp.model.entity.HrajUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDAO {
    private EntityManager persistentStore;

    @Autowired public UserDAO(EntityManager persistentStore) {
        this.persistentStore = persistentStore;
    }

    /**
     * Gets user from database by his id number
     *
     * @param userId user identifier
     * @return user with given id
     */
    public HrajUserEntity getUserById(int userId) {
        assert userId >= 0;

        Query query = persistentStore.createQuery("from HrajUserEntity where id= :id ");
        query.setParameter("id", userId);
        return (HrajUserEntity) query.getSingleResult();
    }

    /**
     * Gets user from database by his user name (login name)
     *
     * @param login user name
     * @return user with given user name
     */
    public HrajUserEntity getUserByLogin(String login) {
        assert login != null && !login.isEmpty();

        Query query = persistentStore.createQuery("from HrajUserEntity where user_name= :login ");
        query.setParameter("login", login);
        return (HrajUserEntity) query.getSingleResult();
    }

    /**
     * Adds new user into DB.
     */
    public void addUser(HrajUserEntity user) {
        persistentStore.persist(user);
    }

    /**
     * Updates user data in DB.
     */
    public void editUser(HrajUserEntity user) {
        persistentStore.persist(user);
    }

    /**
     * Method checks if given username is unique (not present in the database yet).
     *
     * @param userName tested user name
     * @return true if name is not present in the database yet
     */
    public boolean userNameIsUnique(String userName) {
        assert userName != null && !userName.isEmpty();

        Query query = persistentStore.createQuery("from HrajUserEntity where user_name= :login ");
        query.setParameter("login", userName);
        return (query.getResultList().isEmpty());
    }

    public HrajUserEntity getUserToActivate(String activationLink) {
        assert activationLink != null && !activationLink.isEmpty();

        Query query = persistentStore.createQuery("from HrajUserEntity where activation_link= :activationLink ");
        query.setParameter("activationLink", activationLink);
        return (HrajUserEntity) query.getSingleResult();
    }

    /**
     * @return list of all users stored in the DB
     */
    public List<HrajUserEntity> listUsers() {
        Query query = persistentStore.createQuery("from HrajUserEntity");
        return query.getResultList();
    }
}