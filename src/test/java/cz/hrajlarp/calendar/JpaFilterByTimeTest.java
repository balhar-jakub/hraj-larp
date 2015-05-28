package cz.hrajlarp.calendar;

import cz.hrajlarp.service.calendar.JpaCalendar;

/**
 * Test JpaImplementation of the calendar.
 */
public class JpaFilterByTimeTest extends FilterByTimeTest {
    @Override
    public void setUp() {
        calendar = new JpaCalendar();
    }
}
