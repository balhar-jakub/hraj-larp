package cz.hrajlarp.calendar;

import cz.hrajlarp.service.calendar.LarpCalendar;
import cz.hrajlarp.storage.Adapter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test JpaImplementation of the calendar.
 */
public class LarpFilterByTimeTest extends FilterByTimeTest {
    @Autowired Adapter adapter;

    @Override
    public void setUp() {
        calendar = new LarpCalendar(adapter);
    }
}
