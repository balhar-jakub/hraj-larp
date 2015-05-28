package cz.hrajlarp.service.calendar;

import java.util.Collection;

/**
 * Created by Balda on 28. 5. 2015.
 */
public class LarpCalendar implements Calendar {
    @Override
    public Collection<Event> events(Configuration options) {
        return null;
    }

    @Override
    public Collection<Event> events() {
        return null;
    }

    @Override
    public boolean contains(Event event) {
        return false;
    }

    @Override
    public Calendar add(Event event) {
        return null;
    }

    @Override
    public Calendar remove(Event event) {
        return null;
    }
}
