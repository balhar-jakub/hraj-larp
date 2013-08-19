package cz.hrajlarp.model.dao;

import cz.hrajlarp.model.entity.AccountantEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Repository
public class AccountantDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(readOnly=false)
    public void saveOrUpdate(AccountantEntity toSave){
        sessionFactory.getCurrentSession().saveOrUpdate(toSave);
    }

    @Transactional(readOnly=true)
    public AccountantEntity getById(Integer id){
        Query query = sessionFactory.getCurrentSession().createQuery("from AccountantEntity where id = :userId");
        query.setInteger("userId", id);
        return (AccountantEntity) query.uniqueResult();
    }
}
