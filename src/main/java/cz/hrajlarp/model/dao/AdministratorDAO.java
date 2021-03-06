package cz.hrajlarp.model.dao;


import java.util.List;

import cz.hrajlarp.api.GenericBuilder;
import cz.hrajlarp.api.GenericHibernateDAO;
import cz.hrajlarp.api.IBuilder;
import cz.hrajlarp.model.entity.AdministratorEntity;
import cz.hrajlarp.model.entity.HrajUserEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * User: Matheo
 * Date: 12.4.13
 * Time: 20:07
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AdministratorDAO extends GenericHibernateDAO<AdministratorEntity, Integer> {
    @Override
    public IBuilder getBuilder() {
        return new GenericBuilder<AdministratorEntity>(AdministratorEntity.class);
    }

    public boolean isAdministrator(HrajUserEntity user){
        Session session = sessionFactory.openSession();
        try {
            return session.get(
                    AdministratorEntity.class, user.getId()) != null;
        }
        finally { session.close(); }
    }

    public void setAdministrator(int userId){
        AdministratorEntity entity = new AdministratorEntity();
        entity.setId(userId);
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
        finally { session.close(); }
    }
    
    public List<Integer> getAdministratorIds(){
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("select ae.id from AdministratorEntity ae");
            return query.list();
        }
        finally { session.close(); }
    }

    public void removeAdministratorRights(int userId){
        AdministratorEntity entity = new AdministratorEntity();
        entity.setId(userId);
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        }
        finally { session.close(); }
    }
}