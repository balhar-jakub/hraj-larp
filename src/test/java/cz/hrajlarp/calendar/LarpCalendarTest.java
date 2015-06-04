package cz.hrajlarp.calendar;

import cz.hrajlarp.service.calendar.LarpCalendar;
import cz.hrajlarp.service.calendar.LarpEvent;
import cz.hrajlarp.storage.Adapter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Implementation providing JpaCalendar amd LarpEvent as objects under test.
 */
public class LarpCalendarTest extends CalendarTest {
    @Autowired Adapter adapter;

    @Override
    public void setUp() {
        calendar = new LarpCalendar(adapter);

        skyrim = new LarpEvent(new Date(), new Date(), "Prague");
        arkham = new LarpEvent(new Date(), new Date(), "Dankov");
    }
}
