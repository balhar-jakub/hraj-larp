package cz.hrajlarp.model;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Prasek
 * Date: 14.3.13
 * Time: 18:34
 * To change this template use File | Settings | File Templates.
 */
public class ValidGame {
    private String time;
    private int id;
    private String name;
    private String date;
    private String anotation;
    private String author;
    private String image;
    private int addedBy;
    private String menRole;
    private String womenRole;
    private String bothRole;
    private String shortText;
    private String place;
    private String info;
    private String aboutGame;
    private String web;
    private String larpDb;
    private Pattern pattern;
    private Matcher matcher;
    private static final String TIME_24H = "([01]?[0-9]|2[0-3]):[0-5][0-9]";

    /**
     * Validation method for add game form and edit game form
     * @param errors
     */
    public void validate(Errors errors){
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required", "Musíte zadat jméno!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "anotation", "anotation.required","Musíte zadat popis hry!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "author", "name.required","Musíte zadat autora hry!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "image", "name.required","Musíte zadat cestu k obrázku!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "date", "name.required","Musíte zadat datum akce!");
        validateInteger(this.menRole, "menRole", errors);
        validateInteger(this.womenRole, "womenRole", errors);
        validateInteger(this.bothRole, "bothRole", errors);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        Date bDate = null;
        if(this.date != null && !this.date.isEmpty())
        try {
            bDate = new Date(df.parse(this.date).getTime());
            System.out.println("Atribut date byl zvalidovan jako datum.");
        }
        catch (ParseException e){
            errors.rejectValue("date", "date.wrongFormatException", "Musíte zadat datum ve formátu YYYY-MM-DD");
        }

        if (this.time != null && !this.time.isEmpty()){
            pattern = Pattern.compile(TIME_24H);
            matcher = pattern.matcher(this.time);
            if(!matcher.matches()) errors.rejectValue("time", "time.wrongFormatException", "Musíte zadat čas ve formátu HH:MM");
        }
    }

    /**
     * Validation of integer variables stored as string
     * @param atr
     * @param atrName
     * @param errors
     */
    private void validateInteger(String atr, String atrName, Errors errors){
       if (atr != null && !atr.isEmpty()){
            try{
                Integer.parseInt(atr);
            }
            catch (NumberFormatException e) {
                errors.rejectValue(atrName, atrName+".numberFormatException", "Musíte zadat číslo");
            }
       }
    }

    /**
     * converts ValidGame into GameEntity
     * @return
     */
    public GameEntity getGameEntity(){

        GameEntity game = new GameEntity();

        try {
            SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            if (time.isEmpty()) time = "12:00";
            Date changedDate = datetimeFormatter1.parse(date+" "+time);
            game.setDate(new Timestamp(changedDate.getTime()));
        } catch (ParseException e) {
            System.out.println("Error in timestamp converting ValidGame.java.");
        }

        game.setAboutGame(aboutGame);
        game.setAddedBy(1);
        game.setAnotation(anotation);
        game.setAuthor(author);
        game.setImage(image);
        game.setInfo(info);
        game.setLarpDb(larpDb);
        if (!bothRole.isEmpty()) game.setBothRole(Integer.parseInt(bothRole));
        else game.setBothRole(0);
        if (!menRole.isEmpty())game.setMenRole(Integer.parseInt(menRole));
        else game.setMenRole(0);
        if (!womenRole.isEmpty())game.setWomenRole(Integer.parseInt(womenRole));
        else game.setWomenRole(0);
        game.setName(name);
        game.setPlace(place);
        game.setShortText(shortText);
        game.setWeb(web);

        return game;
    }


    public String getLarpDb() {
        return larpDb;
    }

    public void setLarpDb(String larpDb) {
        this.larpDb = larpDb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAnotation() {
        return anotation;
    }

    public void setAnotation(String anotation) {
        this.anotation = anotation;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(int addedBy) {
        this.addedBy = addedBy;
    }

    public String getMenRole() {
        return menRole;
    }

    public void setMenRole(String menRole) {
        this.menRole = menRole;
    }

    public String getWomenRole() {
        return womenRole;
    }

    public void setWomenRole(String womenRole) {
        this.womenRole = womenRole;
    }

    public String getBothRole() {
        return bothRole;
    }

    public void setBothRole(String bothRole) {
        this.bothRole = bothRole;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAboutGame() {
        return aboutGame;
    }

    public void setAboutGame(String aboutGame) {
        this.aboutGame = aboutGame;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {

        return time;
    }

}
