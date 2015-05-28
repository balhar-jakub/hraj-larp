package cz.hrajlarp.service.calendar;

import java.util.Date;

/**
 * Configuration for Calendar with sane defaults and possibility to override them.
 */
public class LarpConfiguration implements Configuration {
    private Date from = new Date();
    private Date to; // Set up to week in future.
    private String location;

    public LarpConfiguration(Date from, Date to){
        this.from = from;
        this.to = to;
    }

    /**
     * Do nothing. Only provide meaningful defaults for Configuration of the calendar.
     */
    public LarpConfiguration() {}

    @Override
    public Configuration from(Date from) {
        this.from = from;

        return this;
    }

    @Override
    public Configuration to(Date to) {
        return null;
    }

    @Override
    public Configuration location(String location) {
        return null;
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
}
