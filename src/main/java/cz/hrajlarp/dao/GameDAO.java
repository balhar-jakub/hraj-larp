package cz.hrajlarp.dao;

import cz.hrajlarp.api.GenericBuilder;
import cz.hrajlarp.api.GenericHibernateDAO;
import cz.hrajlarp.api.IBuilder;
import cz.hrajlarp.entity.Game;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
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
    public List<Game> getFutureGames(){
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Game.class);
        crit.add(Restrictions.ge("date", new Date()));
        crit.addOrder(Order.asc("date"));
        return crit.list();
    }

    /**
     * @return List of all games in past by current date
     */
    public List<Game> getFormerGames(){
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Game.class);
        crit.add(Restrictions.lt("date", new Date()));
        crit.addOrder(Order.desc("date"));
        return crit.list();
    }

    public List<Game> GetGamesMonthInFuture(){
        Session session = sessionFactory.getCurrentSession();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        Timestamp date = new Timestamp(cal.getTimeInMillis());
        Timestamp from = new Timestamp(new Date().getTime());
        Query finalQuery = session.createQuery("from Game where date >= :fromDate and date <= :date and shortText=:shortText order by date");
        finalQuery.setTimestamp("date", date);
        finalQuery.setTimestamp("fromDate", from);
        finalQuery.setString("shortText", FESTIVAL_GAME);
        return finalQuery.list();
    }

    public List<Game> GetGamesTwoWeeksInFuture(){
        Session session = sessionFactory.getCurrentSession();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_MONTH, 2);
        Timestamp date = new Timestamp(cal.getTimeInMillis());
        Timestamp from = new Timestamp(new Date().getTime());
        Query finalQuery = session.createQuery("from Game where date >= :fromDate and date <= :date and shortText=:shortText order by date");
        finalQuery.setTimestamp("fromDate", from);
        finalQuery.setTimestamp("date", date);
        finalQuery.setString("shortText", FESTIVAL_GAME);
        return finalQuery.list();
    }

    public List<Game> getTwoWeeksPast() {
        Session session = sessionFactory.getCurrentSession();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.WEEK_OF_MONTH, -2);
        Timestamp date = new Timestamp(cal.getTimeInMillis());
        Query finalQuery = session.createQuery("from Game where date <= :date and shortText=:shortText order by date");
        finalQuery.setTimestamp("date", date);
        finalQuery.setString("shortText", FESTIVAL_GAME);
        return finalQuery.list();
    }

    public List<Game> getUnapprovedGames() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Game where confirmed = false ");
        return query.list();
    }

    public List<String> getAllActions() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("Select distinct action from Game");
        return query.list();
    }
}
