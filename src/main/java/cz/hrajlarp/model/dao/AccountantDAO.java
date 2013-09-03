package cz.hrajlarp.model.dao;

import cz.hrajlarp.model.entity.AccountantEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Repository
public class AccountantDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(readOnly=false)
    public void saveOrUpdate(AccountantEntity toSave){
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(toSave);
        session.close();
    }

    @Transactional(readOnly=true)
    public AccountantEntity getById(Integer id){
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from AccountantEntity where id = :userId");
        query.setInteger("userId", id);
        AccountantEntity result = (AccountantEntity) query.uniqueResult();
        session.close();
        return result;
    }

    @Transactional(readOnly=true)
    public List<AccountantEntity> getAll(){
        Session session = sessionFactory.openSession();
        Query query = sessionFactory.getCurrentSession().createQuery("from AccountantEntity");
        return query.list();
    }
}
