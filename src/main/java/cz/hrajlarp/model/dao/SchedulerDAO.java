package cz.hrajlarp.model.dao;

import cz.hrajlarp.model.entity.SchedulerEntity;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 22.8.13
 * Time: 22:02
 */
@Repository
public class SchedulerDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(readOnly=false)
    public void saveOrUpdate(SchedulerEntity toSave){
        sessionFactory.getCurrentSession().saveOrUpdate(toSave);
    }

    public List<SchedulerEntity> getAll(){
        Query query = sessionFactory.getCurrentSession().createQuery("from SchedulerEntity");
        return query.list();
    }
}
