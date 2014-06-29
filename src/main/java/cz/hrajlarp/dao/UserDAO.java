package cz.hrajlarp.dao;

import cz.hrajlarp.api.GenericBuilder;
import cz.hrajlarp.api.GenericHibernateDAO;
import cz.hrajlarp.api.IBuilder;
import cz.hrajlarp.entity.HrajUser;
import cz.hrajlarp.utils.HrajRoles;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SuppressWarnings("SimplifiableIfStatement")
@Repository
@Transactional
public class UserDAO extends GenericHibernateDAO<HrajUser, Integer> {
    @Override
    public IBuilder getBuilder() {
        return new GenericBuilder<>(HrajUser.class);
    }

    /**
     * Gets user from database by his user name (login name)
     * @param login user name
     * @return user with given user name
     */
    public HrajUser getUserByLogin(String login){
        return findSingleByCriteria(Restrictions.eq("userName", login));
    }

    /**
     * Method checks if given username is unique (not present in the database yet).
     * @param userName tested user name
     * @return true if name is not present in the database yet
     */
    public boolean userNameIsUnique(String userName){
        return getUserByLogin(userName)==null;
    }
    
    public HrajUser getUserToActivate(String activationLink){
        return findSingleByCriteria(Restrictions.eq("activationLink", activationLink));
    }

    public List<HrajUser> getAdmins() {
        return findByCriteria(Restrictions.eq("role", HrajRoles.ADMIN));
    }

    public List<HrajUser> getUsers() {
        return findByCriteria(Restrictions.eq("role", HrajRoles.USER));
    }

    public List<HrajUser> getAuthEditors() {
        return findByCriteria(Restrictions.eq("role", HrajRoles.AUTHORIZED_EDITOR));
    }

    public List<HrajUser> getSchedulers() {
        return findByCriteria(Restrictions.eq("scheduler", true));
    }

    public List<HrajUser> getPlaceFinders() {
        return findByCriteria(Restrictions.eq("place_finder", true));
    }
}