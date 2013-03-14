package cz.hrajlarp.model;

import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 6.3.13
 * Time: 22:23
 */
public class Game extends GameEntity{

    private String dateAsYMD;
    private String dateAsDM;
    private String dateAsDayName;
    private String dateTime;

    public Game(GameEntity gameEntity){
        init(gameEntity);
        dateAsYMD = getDateAsYMD(getDate());
        dateAsDM = getDateAsMD(getDate());
        dateAsDayName = getDateAsDayName(getDate());
        dateTime = getDateTime(getDate());
    }

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

    public String getDateAsYMD(){
        return dateAsYMD;
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

    public static String getDateTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }
}
