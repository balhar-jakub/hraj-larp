package cz.hrajlarp.model.dao;

import cz.hrajlarp.model.entity.AccountantEntity;
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
public class PlaceFinderDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(readOnly=false)
    public void saveOrUpdate(PlaceFinderEntity toSave){
        sessionFactory.getCurrentSession().saveOrUpdate(toSave);
    }

    @Transactional(readOnly=true)
    public PlaceFinderEntity getById(Integer id){
        Query query = sessionFactory.getCurrentSession().createQuery("from PlaceFinderEntity where id = :userId");
        query.setInteger("userId", id);
        return (PlaceFinderEntity) query.uniqueResult();
    }

    @Transactional(readOnly=true)
    public List<PlaceFinderEntity> getAll(){
        Query query = sessionFactory.getCurrentSession().createQuery("from PlaceFinderEntity");
        return (List<PlaceFinderEntity>) query.list();
    }
}
