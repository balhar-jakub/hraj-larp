package cz.hrajlarp.dao;

import cz.hrajlarp.api.GenericBuilder;
import cz.hrajlarp.api.GenericHibernateDAO;
import cz.hrajlarp.api.IBuilder;
import cz.hrajlarp.entity.Game;
import cz.hrajlarp.entity.HrajUser;
import cz.hrajlarp.entity.UserAttendedGame;
import cz.hrajlarp.entity.UserAttendedGamePk;
import cz.hrajlarp.enums.Gender;
import cz.hrajlarp.enums.Status;
import cz.hrajlarp.service.GameService;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 *
 */
@SuppressWarnings({"SimplifiableIfStatement", "unchecked"})
@Repository
public class UserAttendedGameDAO extends GenericHibernateDAO<UserAttendedGame, UserAttendedGamePk> {
    @Autowired
    GameService gameService;

    @Override
    public IBuilder getBuilder() {
        return new GenericBuilder<>(UserAttendedGame.class);
    }

    public List<UserAttendedGame> getAllAttendeesFromGameWithGivenStatus(Game game, Status... statuses){
        Disjunction acceptableStatuses = Restrictions.disjunction();
        for(Status status : statuses) {
            acceptableStatuses.add(Restrictions.eq("status", status));
        }

        Criteria criteria = getCriteria(UserAttendedGame.class);
        criteria.add(Restrictions.and(
                Restrictions.eq("game", game),
                acceptableStatuses
        ));
        criteria.addOrder(Order.asc("added"));
        return criteria.list();
    }

    public UserAttendedGame getAttendeeWithAnyOfGivenStatuses(Game game, HrajUser user, Status... statuses){
        Disjunction acceptableStatuses = Restrictions.disjunction();
        for(Status status : statuses) {
            acceptableStatuses.add(Restrictions.eq("status", status));
        }

        return findSingleByCriteria(Restrictions.and(
                Restrictions.eq("game", game),
                Restrictions.eq("user", user),
                acceptableStatuses
        ));
    }

    /**
     * Method gets UserAttendedGamesEntity entities,
     * where user was signed for the past events.
     * @return list of UserAttendedGamesEntity entities
     */
    public List<UserAttendedGame> getAttendedInPast(HrajUser user) {
        return getAttendedWithAdditionalRestrictions(user, Restrictions.lt("game.date", new Date()));
    }

    /**
     * Method gets UserAttendedGamesEntity entities,
     * where user is signed for the future events.
     * @return list of UserAttendedGamesEntity entities
     */
    public List<UserAttendedGame> getAttendedInFuture(HrajUser user) {
        return getAttendedWithAdditionalRestrictions(user, Restrictions.ge("game.date", new Date()));
    }

    private List<UserAttendedGame> getAttendedWithAdditionalRestrictions(HrajUser user, Criterion... additionalConstraints) {
        Criteria attended = getCriteria(UserAttendedGame.class);
        attended.add(Restrictions.eq("user", user));
        for(Criterion constraint: additionalConstraints) {
            attended.add(constraint);
        }
        attended.add(Restrictions.or(
                Restrictions.eq("status", Status.SUBSTITUTE),
                Restrictions.eq("status", Status.LOGGED)
        ));
        return attended.list();
    }

    public UserAttendedGame getByVS(String variableSymbol) {
        return findSingleByCriteria(Restrictions.eq("variableSymbol", variableSymbol));
    }

    public boolean isSignedAsSubstitute(Game game, HrajUser user) {
        return getAttendeeWithAnyOfGivenStatuses(game, user, Status.SUBSTITUTE) != null;
    }

    public boolean isSignedAsRegular(Game game, HrajUser user) {
        return getAttendeeWithAnyOfGivenStatuses(game, user, Status.LOGGED) != null;
    }

    public UserAttendedGame getFirstSubstituteForGame(Game game, Gender gender) {
        Criteria substitutesOfGivenGender = getCriteria(UserAttendedGame.class);
        substitutesOfGivenGender.add(Restrictions.and(
                Restrictions.eq("game", game),
                Restrictions.eq("status", Status.SUBSTITUTE)
        ));
        substitutesOfGivenGender.addOrder(Order.asc("added"));
        if(gender != Gender.BOTH) {
            substitutesOfGivenGender.add(Restrictions.eq("user.gender", gender));
        }
        List<UserAttendedGame> results = substitutesOfGivenGender.list();
        return results.size() > 0 ? results.get(0) : null;
    }

    public Long getNextVariableSymbol(){
        Session session = sessionFactory.openSession();
        Query query = session.createSQLQuery("select nextval('hraj_user_attended_game_seq')");
        return ((BigInteger) query.uniqueResult()).longValue();
    }
}
