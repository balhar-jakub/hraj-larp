package cz.hrajlarp.dao;

import cz.hrajlarp.api.GenericBuilder;
import cz.hrajlarp.api.GenericHibernateDAO;
import cz.hrajlarp.api.IBuilder;
import cz.hrajlarp.entity.Game;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 */
@SuppressWarnings("unchecked")
@Repository
@Transactional
public class GameDAO extends GenericHibernateDAO<Game, Integer>{
    private String FESTIVAL_GAME = "Festivalová";

    @Override
    public IBuilder getBuilder() {
        return new GenericBuilder<>(Game.class);
    }

    /**
     * @return List of all games in future from current date
     */
    public List<Game> getGamesInFuture(){
        Criteria gamesInFuture = getCriteria(Game.class);
        gamesInFuture.add(Restrictions.ge("date", new Date()));
        gamesInFuture.addOrder(Order.asc("date"));
        return gamesInFuture.list();
    }

    /**
     * @return List of all games in past by current date
     */
    public List<Game> getGamesInPast(){
        Criteria gamesInPast = getCriteria(Game.class);
        gamesInPast.add(Restrictions.lt("date", new Date()));
        gamesInPast.addOrder(Order.desc("date"));
        return gamesInPast.list();
    }

    public List<Game> getFestivalGamesInUpcomingMonth(){
        Calendar to = Calendar.getInstance();
        to.add(Calendar.MONTH, 1);
        Timestamp monthInFuture = new Timestamp(to.getTimeInMillis());
        Timestamp now = new Timestamp(new Date().getTime());

        return getFestivalGamesInTimeFrame(now, monthInFuture);
    }

    public List<Game> getFestivalGamesInUpcomingTwoWeeks(){
        Calendar to = Calendar.getInstance();
        to.add(Calendar.WEEK_OF_MONTH, 2);
        Timestamp twoWeeksInFuture = new Timestamp(to.getTimeInMillis());
        Timestamp now = new Timestamp(new Date().getTime());

        return getFestivalGamesInTimeFrame(now, twoWeeksInFuture);
    }

    public List<Game> getFestivalGamesOlderThanTwoWeeks() {
        Calendar to = Calendar.getInstance();
        to.add(Calendar.WEEK_OF_MONTH, -2);
        Timestamp twoWeeksInPast = new Timestamp(to.getTimeInMillis());

        Criteria gamesOlderThanTwoWeeks = getCriteria(Game.class);
        gamesOlderThanTwoWeeks.add(Restrictions.le("date", twoWeeksInPast));
        gamesOlderThanTwoWeeks.add(Restrictions.eq("shortText", FESTIVAL_GAME));
        gamesOlderThanTwoWeeks.addOrder(Order.asc("date"));
        return gamesOlderThanTwoWeeks.list();
    }

    public List<Game> getUnapprovedGames() {
        Criteria unapprovedGames = getCriteria(Game.class);
        unapprovedGames.add(Restrictions.eq("confirmed", false));
        return unapprovedGames.list();
    }

    public List<String> getAllActions() {
        Criteria criteria = getCriteria(String.class);
        criteria.setProjection(Projections.distinct(Projections.property("action")));
        return criteria.list();
    }

    private List<Game> getFestivalGamesInTimeFrame(Timestamp from, Timestamp to) {
        Criteria festivalGamesInTimeFrame = getCriteria(Game.class);
        festivalGamesInTimeFrame.add(Restrictions.ge("date", from));
        festivalGamesInTimeFrame.add(Restrictions.le("date", to));
        festivalGamesInTimeFrame.add(Restrictions.eq("shortText", FESTIVAL_GAME));
        festivalGamesInTimeFrame.addOrder(Order.asc("date"));
        return festivalGamesInTimeFrame.list();
    }
}
