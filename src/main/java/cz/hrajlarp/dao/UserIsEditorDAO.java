package cz.hrajlarp.dao;

import cz.hrajlarp.api.GenericBuilder;
import cz.hrajlarp.api.GenericHibernateDAO;
import cz.hrajlarp.api.IBuilder;
import cz.hrajlarp.entity.Game;
import cz.hrajlarp.entity.HrajUser;
import cz.hrajlarp.entity.UserGamePK;
import cz.hrajlarp.entity.UserIsEditor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@SuppressWarnings("unchecked")
@Repository
public class UserIsEditorDAO extends GenericHibernateDAO<UserIsEditor, Integer> {
    @Override
    public IBuilder getBuilder() {
        return new GenericBuilder<>(UserIsEditor.class);
    }

    public boolean isEditorOfGame(HrajUser user, Game game){
         return getUserIsEditor(user, game) != null;
    }

    public boolean isEditor(HrajUser user){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from UserIsEditor uie where uie.userId= :userId");
        query.setParameter("userId", user.getId());
        return !query.list().isEmpty();
    }

    public Object getUserIsEditor(HrajUser user, Game game){
        Session session = sessionFactory.getCurrentSession();
        return session.get(
                UserIsEditor.class,
                new UserGamePK(user.getId(), game.getId())
        );
    }
    
    /**
     * @return list of editors by given game
     */
    public List<HrajUser> getEditorsByGameId(int gameId){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select user " +
                "from HrajUser user, UserIsEditor uie " +
                "where uie.gameId=:gameId and user.id = uie.userId");
        query.setParameter("gameId", gameId);
        return query.list();
    }
}