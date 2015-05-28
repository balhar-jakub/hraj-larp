package cz.hrajlarp.calendar;

import cz.hrajlarp.service.calendar.Calendar;
import cz.hrajlarp.service.calendar.Configuration;
import cz.hrajlarp.service.calendar.Event;
import cz.hrajlarp.service.calendar.LarpConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * Acceptance tests for filtering by the date.
 */
public abstract class FilterByTimeTest {
    Calendar calendar;

    Event happensInOneWeek;
    Event happenedTwoWeeksAgo;
    Event happensInOneMonth;
    Event happensNow;

    @Before
    public void setUp() {
        calendar.add(happenedTwoWeeksAgo);
        calendar.add(happensInOneMonth);
        calendar.add(happensInOneWeek);
        calendar.add(happensNow);
    }

    @Test
    public void retrieveOnlyFutureEvents() {
        Configuration futureEventsOnly = new LarpConfiguration().from(new Date());
        Collection<Event> futureEvents = calendar.events(futureEventsOnly);
        assertThat(futureEvents, hasSize(3));
    }
}
