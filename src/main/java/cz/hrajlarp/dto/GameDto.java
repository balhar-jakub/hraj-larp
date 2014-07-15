package cz.hrajlarp.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by jbalhar on 7/1/2014.
 */
public class GameDto {
    public Integer id;

    private boolean copied;
    private MultipartFile image;
    @NotBlank
    private String name;
    private String annotation;
    private String shortText;
    private String aboutGame;
    private String info;
    private String author;
    private String place;
    private String web;
    private String action;

    private String ordinaryPlayerText;
    private String replacementsText;
    private String registeredSubstitute;

    private String date;
    private String time;
    private String dateAsDM;
    private String dateAsDayName;
    private String registrationStartedDate;
    private String registrationStartedTime;

    private PlayerSpacesDto players;

    private boolean mailProhibition;

    public String getDateAsDM() {
        return dateAsDM;
    }

    public void setDateAsDM(String dateAsDM) {
        this.dateAsDM = dateAsDM;
    }

    public String getDateAsDayName() {
        return dateAsDayName;
    }

    public void setDateAsDayName(String dateAsDayName) {
        this.dateAsDayName = dateAsDayName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public boolean isCopied() {
        return copied;
    }

    public void setCopied(boolean copied) {
        this.copied = copied;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getAboutGame() {
        return aboutGame;
    }

    public void setAboutGame(String aboutGame) {
        this.aboutGame = aboutGame;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getOrdinaryPlayerText() {
        return ordinaryPlayerText;
    }

    public void setOrdinaryPlayerText(String ordinaryPlayerText) {
        this.ordinaryPlayerText = ordinaryPlayerText;
    }

    public String getReplacementsText() {
        return replacementsText;
    }

    public void setReplacementsText(String replacementsText) {
        this.replacementsText = replacementsText;
    }

    public String getRegisteredSubstitute() {
        return registeredSubstitute;
    }

    public void setRegisteredSubstitute(String registeredSubstitute) {
        this.registeredSubstitute = registeredSubstitute;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRegistrationStartedTime() {
        return registrationStartedTime;
    }

    public void setRegistrationStartedTime(String registrationStartedTime) {
        this.registrationStartedTime = registrationStartedTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRegistrationStartedDate() {
        return registrationStartedDate;
    }

    public void setRegistrationStartedDate(String registrationStartedDate) {
        this.registrationStartedDate = registrationStartedDate;
    }

    public boolean isMailProhibition() {
        return mailProhibition;
    }

    public void setMailProhibition(boolean mailProhibition) {
        this.mailProhibition = mailProhibition;
    }

    public PlayerSpacesDto getPlayers() {
        return players;
    }

    public void setPlayers(PlayerSpacesDto players) {
        this.players = players;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
