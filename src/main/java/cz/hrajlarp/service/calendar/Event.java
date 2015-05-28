package cz.hrajlarp.service.calendar;

import java.util.Date;

/**
 * Common interface for all events. Any user of this package can provide its own implementation of an Event.
 */
public interface Event {
    /**
     * Unique identifier of an event.
     * @return Unique identifier of the event in its string repreentation.
     */
    String uuid();

    /**
     * It returns date from which the Event starts
     * @return Valid start Date of the event.
     */
    Date from();

    /**
     * It returns date on which the Event ends.
     * @return Valid end Date of the event.
     */
    Date to();

    /**
     * Every event must be able to provide its own location.
     * @return Valid location of an event.
     */
    String location();
}
