package cz.hrajlarp.calendar;

import cz.hrajlarp.AcceptanceTest;
import cz.hrajlarp.service.calendar.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.Date;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * It tests usage of the calendar for adding new events into the calendar
 */
public abstract class CalendarTest extends AcceptanceTest {
    Calendar calendar;
    Event skyrim;
    Event arkham;

    @Before
    public abstract void setUp();

    @Test
    public void addEventToTheCalendar() {
        calendar.add(skyrim);
        assertThat(calendar.contains(skyrim), is(true));
    }

    @Test(expected = RuntimeException.class)
    public void addEventTwiceToCalendar() {
        calendar.add(skyrim);
        calendar.add(skyrim);
    }

    @Test
    public void removePresentEventFromTheCalendar() {
        calendar.add(skyrim);
        calendar.remove(skyrim);
        assertThat(calendar.contains(skyrim), is(false));
    }

    @Test(expected = RuntimeException.class)
    public void removeMissingEventFromTheCalendar() {
        calendar.remove(skyrim);
    }

    @Test
    public void retrieveAllEventsPresentInCalendar() {
        calendar.add(skyrim);
        calendar.add(arkham);

        Collection<Event> allEvents = calendar.events();
        assertThat(allEvents, hasSize(2));
        assertThat(allEvents, containsInAnyOrder(skyrim, arkham));
    }
}