package cz.hrajlarp.model;


import java.util.List;

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
public class AdministratorDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(readOnly=true)
    public boolean isAdministrator(HrajUserEntity user){
        Session session = sessionFactory.openSession();
        try {
            return session.get(
                    AdministratorEntity.class, user.getId()) != null;
        }
        finally { session.close(); }
    }

    @Transactional(readOnly=false)
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
    
    @Transactional(readOnly=true)
    public List<Integer> getAdministratorIds(){
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("select ae.id from AdministratorEntity ae");
            return query.list();
        }
        finally { session.close(); }
    }
}
