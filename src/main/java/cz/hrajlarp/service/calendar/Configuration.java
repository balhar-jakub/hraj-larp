package cz.hrajlarp.service.calendar;

import java.util.Date;

/**
 * Every configuration passable to Calendar must contain all methods from Event as a way to filter Events.
 */
public interface Configuration extends Event {
    /**
     * Fluent api method used to set from bound.
     *
     * @param from Date from which the events should be returned.
     * @return Itself for further chaining.
     */
    Configuration from(Date from);

    /**
     * Fluent api method used to set to bound.
     *
     * @param to Date to which the events should be returned.
     * @return Itself for further chaining.
     */
    Configuration to(Date to);

    /**
     * Fluent api method used to set location restriction.
     *
     * @param location Location of events to retrieve.
     * @return Itself for further chaining.
     */
    Configuration location(String location);
}
