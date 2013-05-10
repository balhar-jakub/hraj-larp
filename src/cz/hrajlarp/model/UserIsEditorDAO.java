package cz.hrajlarp.model;

import java.util.List;

import com.sun.org.apache.bcel.internal.generic.RETURN;
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
 * Time: 15:52
 * To change this template use File | Settings | File Templates.
 */
@Component
public class UserIsEditorDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public boolean isEditorOfGame(HrajUserEntity user, GameEntity game){
         return getUserIsEditor(user, game) != null;
    }

    @Transactional(readOnly=true)
    public boolean isEditor(HrajUserEntity user){
        if(user == null) return false;

        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("from UserIsEditorEntity uie where uie.userId= :userId");
            query.setParameter("userId", user.getId());
            return !query.list().isEmpty();
        }
        finally { session.close(); }
    }

    @Transactional(readOnly=true)
    public Object getUserIsEditor(HrajUserEntity user, GameEntity game){
        if(user == null || game == null) return null;

        Session session = sessionFactory.openSession();
        try {
            return session.get(
                    UserIsEditorEntity.class,
                    new UserGamePK(user.getId(), game.getId())
            );
        }
        finally { session.close(); }
    }


    /**
     * This method adds new UserIsEditor record into database
     * @param record
     */
    @Transactional(readOnly=false)
    public void addUserIsEditor(UserIsEditorEntity record){
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(record);
            session.getTransaction().commit();
        }
        finally { session.close(); }
    }

    /**
     * Delete method for UserIsEditor table.
     * @param record object for delete.
     */
    @Transactional(readOnly=false)
    public void deleteUserIsEditor(UserIsEditorEntity record) {
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.delete(record);
            session.getTransaction().commit();
        }
        finally { session.close(); }
    }
    
    @Transactional(readOnly=true)
    public List<Integer> getEditorIds(GameEntity game){
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("select ue.userId from UserIsEditorEntity ue where gameId= :gameId");
            query.setParameter("gameId", game.getId());
            return query.list();
        }
        finally { session.close(); }
    }

    
    /**
     * @param gameId
     * @return list of editors by given game
     */
    @Transactional(readOnly = true)
    public List<HrajUserEntity> getEditorsByGameId(int gameId){
        Session session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("select user " +
                    "from HrajUserEntity user, UserIsEditorEntity uie " +
                    "where uie.gameId=:gameId and user.id = uie.userId");
            query.setParameter("gameId", gameId);
            return query.list();
        }
        finally { session.close(); }
    }
}