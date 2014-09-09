package cz.hrajlarp.model.dao;

import cz.hrajlarp.model.entity.PlaceFinderEntity;
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
public class PlaceFinderDAO {
    @Autowired
    private SessionFactory sessionFactory;

    public void saveOrUpdate(PlaceFinderEntity toSave){
        sessionFactory.getCurrentSession().saveOrUpdate(toSave);
    }

    public PlaceFinderEntity getById(Integer id){
        PlaceFinderEntity result = null;
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("from PlaceFinderEntity where id = :userId");
            query.setInteger("userId", id);
            result = (PlaceFinderEntity) query.uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    public List<PlaceFinderEntity> getAll(){
        List<PlaceFinderEntity> result = null;
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("from PlaceFinderEntity");
            result = query.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }
}