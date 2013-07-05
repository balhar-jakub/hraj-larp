package cz.hrajlarp.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    
    public static String getDateAsDMYHM(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return sdf.format(date);
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
    
    public static boolean isLessThanDayToReg(Timestamp regStart) {
    	Date now = Calendar.getInstance().getTime();
		if(now.after(getDayAgoDate(regStart)))
			return true;
		else 
			return false;
    }
    
    public static Timestamp getDayAgoDate(Timestamp original) {
    	Calendar c = Calendar.getInstance();
    	c.setTime(original);
		c.add(Calendar.DAY_OF_MONTH, -1);
		return new Timestamp(c.getTime().getTime());
    }
}
