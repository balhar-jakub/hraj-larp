package cz.hrajlarp.utils;

import java.sql.Timestamp;
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
        return nowTimestamp.after(timestamp);
    }
}
