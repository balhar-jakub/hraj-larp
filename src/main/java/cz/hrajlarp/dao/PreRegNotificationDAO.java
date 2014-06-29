package cz.hrajlarp.dao;

import cz.hrajlarp.api.GenericBuilder;
import cz.hrajlarp.api.GenericHibernateDAO;
import cz.hrajlarp.api.IBuilder;
import cz.hrajlarp.entity.Game;
import cz.hrajlarp.entity.HrajUser;
import cz.hrajlarp.entity.PreRegNotification;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;


@Component
public class PreRegNotificationDAO extends GenericHibernateDAO<PreRegNotification, Integer> {
    @Override
    public IBuilder getBuilder() {
        return new GenericBuilder<>(PreRegNotification.class);
    }

    public boolean isSubscribedForPreReg(HrajUser user, Game game){
        return findSingleByCriteria(
                Restrictions.and(
                        Restrictions.eq("userId", user.getId()),
                        Restrictions.eq("gameId", game.getId())
                )
        ) != null;
    }
}