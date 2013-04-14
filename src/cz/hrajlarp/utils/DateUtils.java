package cz.hrajlarp.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Matheo
 * Date: 9.3.13
 * Time: 18:37
 * To change this template use File | Settings | File Templates.
 */
public class DateUtils {

    public static boolean isFuture(Timestamp timestamp){
        Date now = new Date();
        Timestamp nowTimestamp= new Timestamp(now.getTime());
        return nowTimestamp.before(timestamp);
    }

    public static Date stringsToDate(String day, String time){
        SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = datetimeFormatter1.parse(day+" "+time);
        } catch (ParseException e) {
            System.out.println("Error in timestamp converting DateUtils.java.");
        }
        return date;
    }
}
