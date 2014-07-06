package cz.hrajlarp.service;

import cz.hrajlarp.entity.Game;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jbalhar on 7/6/2014.
 */
@Service
public class DateService {
    public String getDateAsDMY(Timestamp date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date);
    }

    public String getDateAsYMD(Timestamp date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public String getTimeAsHM(Timestamp date){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }

    public String getDateAsDMYHM(Timestamp date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return sdf.format(date);
    }

    public String getDateAsDayName(Timestamp date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");    // day name
        return sdf.format(date);
    }

    public String getDateAsDM(Timestamp date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM");
        return sdf.format(date);
    }

    public boolean isDateInFuture(Timestamp date) {
        return date.after(new Date());
    }

    public boolean isLessThanDayToReg(Timestamp regStart) {
        Date now = Calendar.getInstance().getTime();
        if(now.after(getDayAgoDate(regStart)))
            return true;
        else
            return false;
    }

    public Timestamp getDayAgoDate(Timestamp original) {
        Calendar c = Calendar.getInstance();
        c.setTime(original);
        c.add(Calendar.DAY_OF_MONTH, -1);
        return new Timestamp(c.getTime().getTime());
    }
}
