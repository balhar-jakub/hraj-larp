package cz.hrajlarp.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 6.3.13
 * Time: 22:23
 */
public class Game extends GameEntity{

    private String dateAsDMY;   // date in format dd.MM.yyyy
    private String dateAsDM;    // date in format dd.MM
    private String dateAsDayName;   // day name
    private String dateTime;    // time as HH:mm

    public Game(GameEntity gameEntity){
        init(gameEntity);
        dateAsDMY = getDateAsDMY(getDate());
        dateAsDM = getDateAsDM(getDate());
        dateAsDayName = getDateAsDayName(getDate());
        dateTime = getDateTime(getDate());
    }

    /**
     * Initialization of super class attributes
     * @param gameEntity holds data to set in super class setters
     */
    private void init(GameEntity gameEntity){
        super.setId(gameEntity.getId());
        super.setName(gameEntity.getName());
        super.setDate(gameEntity.getDate());
        super.setImage(gameEntity.getImage());
        super.setAboutGame(gameEntity.getAboutGame());
        super.setAddedBy(gameEntity.getAddedBy());
        super.setAnotation(gameEntity.getAnotation());
        super.setAuthor(gameEntity.getAuthor());
        super.setMenRole(gameEntity.getMenRole());
        super.setWomenRole(gameEntity.getWomenRole());
        super.setBothRole(gameEntity.getBothRole());
        super.setShortText(gameEntity.getShortText());
        super.setPlace(gameEntity.getPlace());
        super.setInfo(gameEntity.getInfo());
        super.setWeb(gameEntity.getWeb());
        super.setLarpDb(gameEntity.getLarpDb());
    }

    public String getDateAsDMY(){
        return dateAsDMY;
    }

    public String getDateAsDM(){
        return dateAsDM;
    }

    public String getDateAsDayName(){
        return dateAsDayName;
    }

    public String getDateTime(){
        return dateTime;
    }

    public static String getDateAsDMY(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date);
    }

    public static String getDateAsDM(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM");
        return sdf.format(date);
    }

    public static String getDateAsDayName(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");    // day name
        return sdf.format(date);
    }

    public static String getDateTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }
}
