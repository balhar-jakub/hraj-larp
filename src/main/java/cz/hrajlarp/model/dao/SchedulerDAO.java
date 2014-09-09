package cz.hrajlarp.model.dao;

import cz.hrajlarp.model.entity.SchedulerEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    public void saveOrUpdate(SchedulerEntity toSave){
        Session session = sessionFactory.openSession();
        session.saveOrUpdate(toSave);
        session.close();
    }

    public List<SchedulerEntity> getAll(){
        Session session = sessionFactory.openSession();
        List<SchedulerEntity> result = new ArrayList<SchedulerEntity>();
        Query query = session.createQuery("from SchedulerEntity");
        result = query.list();
        session.close();
        return result;
    }
}
