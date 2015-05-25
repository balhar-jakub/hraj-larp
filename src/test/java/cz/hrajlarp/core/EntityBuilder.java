package cz.hrajlarp.core;

import cz.hrajlarp.model.Gender;
import cz.hrajlarp.model.entity.HrajUserEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

/**
 * Simple builder for entities used as a part of tests.
 */
public class EntityBuilder {
    @Autowired
    EntityManager persistenceStore;

    public HrajUserEntity user(String email, String firstName, String lastName, String userName){
        HrajUserEntity user = new HrajUserEntity();
        user.setActivated(true);
        user.setEmail(email);
        user.setGender(Gender.MEN.ordinal());
        user.setLastName(lastName);
        user.setName(firstName);
        user.setUserName(userName);
        user.setPassword("TestPwd");
        user.setPhone("723354354");

        persistenceStore.persist(user);
        return user;
    }
}
