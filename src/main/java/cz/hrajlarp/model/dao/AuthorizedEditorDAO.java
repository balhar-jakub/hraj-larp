package cz.hrajlarp.model.dao;

import cz.hrajlarp.model.entity.AuthorizedEditorEntity;
import cz.hrajlarp.model.entity.HrajUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Matheo
 * Date: 28.4.13
 * Time: 10:55
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class AuthorizedEditorDAO {
    private EntityManager persistentStore;

    @Autowired public AuthorizedEditorDAO(EntityManager persistentStore) {
        this.persistentStore = persistentStore;
    }

    public boolean isAuthorizedEditor(HrajUserEntity user){
        return persistentStore.find(AuthorizedEditorEntity.class, user.getId()) != null;
    }

    public void setAuthorizedEntity(int userId){
        AuthorizedEditorEntity entity = new AuthorizedEditorEntity();
        entity.setId(userId);
        persistentStore.persist(entity);
    }

    public void removeAuthorizedEntityRights(int userId){
        AuthorizedEditorEntity entity = new AuthorizedEditorEntity();
        entity.setId(userId);
        persistentStore.remove(entity);
    }

    /**
     * @return list of all authorized editors stored in the DB
     */
    public List<Integer> listAuthorizedEditors(){
        Query query = persistentStore.createQuery("select aued.id from AuthorizedEditorEntity aued");
        return query.getResultList();
    }
}
