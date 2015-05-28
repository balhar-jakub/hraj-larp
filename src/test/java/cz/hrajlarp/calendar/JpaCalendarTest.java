package cz.hrajlarp.calendar;

import cz.hrajlarp.service.calendar.JpaCalendar;
import cz.hrajlarp.service.calendar.LarpEvent;

import java.util.Date;

/**
 * Implementation providing JpaCalendar amd LarpEvent as objects under test.
 */
public class JpaCalendarTest extends CalendarTest {
    @Override
    public void setUp() {
        calendar = new JpaCalendar();

        skyrim = new LarpEvent(new Date(), new Date(), "Prague");
    }
}
