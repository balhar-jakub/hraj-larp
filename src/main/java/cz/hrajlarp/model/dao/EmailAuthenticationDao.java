package cz.hrajlarp.model.dao;

import cz.hrajlarp.model.entity.EmailAuthenticatitonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Simple Dao.
 */
@Repository
public class EmailAuthenticationDao {
    private EntityManager persistentStore;

    @Autowired public EmailAuthenticationDao(EntityManager persistentStore) {
        this.persistentStore = persistentStore;
    }

    public EmailAuthenticatitonEntity getByActivationLink(String authToken) {
        Query activatingAccount = persistentStore.createQuery("from EmailAuthenticationEntity where auth_token = :authToken");
        activatingAccount.setParameter("authToken", authToken);
        return (EmailAuthenticatitonEntity) activatingAccount.getSingleResult();
    }

    public void persist(EmailAuthenticatitonEntity toSave) {
        persistentStore.persist(toSave);
    }
}
