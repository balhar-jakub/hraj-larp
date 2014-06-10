package cz.hrajlarp.model.dao;

import cz.hrajlarp.api.GenericBuilder;
import cz.hrajlarp.api.GenericHibernateDAO;
import cz.hrajlarp.api.IBuilder;
import cz.hrajlarp.model.entity.EmailAuthenticatitonEntity;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Simple Dao.
 */
@Repository
public class EmailAuthenticationDao extends GenericHibernateDAO<EmailAuthenticatitonEntity, Integer>{
    @Override
    public IBuilder getBuilder() {
        return new GenericBuilder<EmailAuthenticatitonEntity>(EmailAuthenticatitonEntity.class);
    }

    public EmailAuthenticatitonEntity getByActivationLink(String authToken) {
        Criteria criteria = getBuilder().build().getExecutableCriteria(sessionFactory.getCurrentSession());
        criteria.add(Restrictions.eq("auth_token", authToken));
        return (EmailAuthenticatitonEntity) criteria.uniqueResult();
    }
}
