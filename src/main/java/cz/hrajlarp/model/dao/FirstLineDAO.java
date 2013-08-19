package cz.hrajlarp.model.dao;

import cz.hrajlarp.model.entity.AccountantEntity;
import cz.hrajlarp.model.entity.FirstLineEntity;
import cz.hrajlarp.model.entity.PlaceFinderEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Repository
public class FirstLineDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(readOnly=false)
    public void saveOrUpdate(FirstLineEntity toSave){
        sessionFactory.getCurrentSession().saveOrUpdate(toSave);
    }

    @Transactional(readOnly=true)
    public FirstLineEntity getById(Integer id){
        Query query = sessionFactory.getCurrentSession().createQuery("from FirstLineEntity where id = :userId");
        query.setInteger("userId", id);
        return (FirstLineEntity) query.uniqueResult();
    }

    @Transactional(readOnly=true)
    public List<FirstLineEntity> getAll(){
        Query query = sessionFactory.getCurrentSession().createQuery("from FirstLineEntity");
        return (List<FirstLineEntity>) query.list();
    }
}
