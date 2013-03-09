package cz.hrajlarp.utils;

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

    public static String getDateAsYMD(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        return sdf.format(date);
    }

    public static String getDateAsMD(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd");
        return sdf.format(date);
    }

    public static String getDateAsDayName(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        return sdf.format(date);
    }

    public static String getTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }
}
