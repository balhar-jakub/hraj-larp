package cz.hrajlarp.model.dao;

import cz.hrajlarp.model.entity.GameEntity;
import cz.hrajlarp.model.entity.HrajUserEntity;
import cz.hrajlarp.model.entity.UserGamePK;
import cz.hrajlarp.model.entity.UserIsEditorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Matheo
 * Date: 12.4.13
 * Time: 15:52
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class UserIsEditorDAO {
    private EntityManager persistentStore;

    @Autowired
    public UserIsEditorDAO(EntityManager persistentStore) {
        this.persistentStore = persistentStore;
    }

    public boolean isEditorOfGame(HrajUserEntity user, GameEntity game) {
        return getUserIsEditor(user, game) != null;
    }

    public boolean isEditor(HrajUserEntity user) {
        assert user != null;

        Query query = persistentStore.createQuery("from UserIsEditorEntity uie where uie.userId= :userId");
        query.setParameter("userId", user.getId());
        return !query.getResultList().isEmpty();
    }

    public Object getUserIsEditor(HrajUserEntity user, GameEntity game) {
        assert user != null && game != null;

        return persistentStore.find(
                UserIsEditorEntity.class,
                new UserGamePK(user.getId(), game.getId())
        );
    }

    /**
     * This method adds new UserIsEditor record into database
     *
     * @param record
     */
    public void addUserIsEditor(UserIsEditorEntity record) {
        persistentStore.persist(record);
    }

    /**
     * Delete method for UserIsEditor table.
     *
     * @param record object for delete.
     */
    public void deleteUserIsEditor(UserIsEditorEntity record) {
        persistentStore.remove(record);
    }

    public List<Integer> getEditorIds(GameEntity game) {
        Query query = persistentStore.createQuery("select ue.userId from UserIsEditorEntity ue where gameId= :gameId");
        query.setParameter("gameId", game.getId());
        return query.getResultList();
    }


    /**
     * @param gameId
     * @return list of editors by given game
     */
    public List<HrajUserEntity> getEditorsByGameId(int gameId) {
        Query query = persistentStore.createQuery("select user from HrajUserEntity user, UserIsEditorEntity uie " +
                "where uie.gameId=:gameId and user.id = uie.userId");
        query.setParameter("gameId", gameId);
        return query.getResultList();
    }
}