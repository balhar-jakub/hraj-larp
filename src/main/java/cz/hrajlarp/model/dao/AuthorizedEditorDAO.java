package cz.hrajlarp.model.dao;

import cz.hrajlarp.model.entity.AuthorizedEditorEntity;
import cz.hrajlarp.model.entity.HrajUserEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Matheo
 * Date: 28.4.13
 * Time: 10:55
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AuthorizedEditorDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(readOnly=true)
    public boolean isAuthorizedEditor(HrajUserEntity user){
        Session session = sessionFactory.openSession();
        try {
            return session.get(
                    AuthorizedEditorEntity.class, user.getId()) != null;
        }
        finally { session.close(); }
    }

    @Transactional(readOnly=false)
    public void setAuthorizedEntity(int userId){
        AuthorizedEditorEntity entity = new AuthorizedEditorEntity();
        entity.setId(userId);
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
        finally { session.close(); }
    }

    @Transactional(readOnly=false)
    public void removeAuthorizedEntityRights(int userId){
        AuthorizedEditorEntity entity = new AuthorizedEditorEntity();
        entity.setId(userId);
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        }
        finally { session.close(); }
    }

    /**
     * @return list of all authorized editors stored in the DB
     */
    public List<Integer> listAuthorizedEditors(){
        Session session = sessionFactory.openSession();
        try{
            Query query = session.createQuery("select aued.id from AuthorizedEditorEntity aued");
            return query.list();
        }
        finally { session.close(); }
    }
}
