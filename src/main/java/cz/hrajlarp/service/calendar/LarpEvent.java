package cz.hrajlarp.service.calendar;

import java.util.Date;

/**
 * Simle implementation of event for testing purposes.
 */
public class LarpEvent implements Event {
    private Date from;
    private Date to;
    private String location;

    public LarpEvent(Date from, Date to, String location) {
        this.from = from;
        this.to = to;
        this.location = location;
    }

    @Override
    public String uuid() {
        return null;
    }

    @Override
    public Date from() {
        return from;
    }

    @Override
    public Date to() {
        return to;
    }

    @Override
    public String location() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LarpEvent larpEvent = (LarpEvent) o;

        if (from != null ? !from.equals(larpEvent.from) : larpEvent.from != null) return false;
        if (to != null ? !to.equals(larpEvent.to) : larpEvent.to != null) return false;
        return !(location != null ? !location.equals(larpEvent.location) : larpEvent.location != null);

    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
