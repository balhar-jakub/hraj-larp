package cz.hrajlarp.entity;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;


/**
 *
 */
@SuppressWarnings("RedundantIfStatement")
@Table(name = "game", schema = "public")
@Entity
public class Game {
    private int id;

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_key_gen")
    @SequenceGenerator(name = "id_key_gen", sequenceName = "hraj_game_id_seq", allocationSize = 1)
    @Column(name = "id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;

    @Column(name = "name")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Timestamp date;

    @Column(name = "date")
    @Basic
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    private String time;

    @Transient
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String anotation;

    @Column(name = "anotation")
    @Basic
    public String getAnotation() {
        return anotation;
    }

    public void setAnotation(String anotation) {
        this.anotation = anotation;
    }

    private String author;

    @Column(name = "author")
    @Basic
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private boolean confirmed;

    @Column(name = "confirmed")
    @Basic
    public boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    private String image;

    @Column(name = "image")
    @Basic
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private int addedBy;

    @Column(name = "added_by")
    @Basic
    public int getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(int addedBy) {
        this.addedBy = addedBy;
    }

    private int menRole;

    @Column(name = "men_role")
    @Basic
    public int getMenRole() {
        return menRole;
    }

    public void setMenRole(int menRole) {
        this.menRole = menRole;
    }

    private int womenRole;

    @Column(name = "women_role")
    @Basic
    public int getWomenRole() {
        return womenRole;
    }

    public void setWomenRole(int womenRole) {
        this.womenRole = womenRole;
    }

    private int bothRole;

    @Column(name = "both_role")
    @Basic
    public int getBothRole() {
        return bothRole;
    }

    public void setBothRole(int bothRole) {
        this.bothRole = bothRole;
    }

    private String shortText;

    @Column(name = "short_text")
    @Basic
    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    private String place;

    @Column(name = "place")
    @Basic
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    private String info;

    @Column(name = "info")
    @Basic
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private String aboutGame;

    @Column(name = "about_game")
    @Basic
    public String getAboutGame() {
        return aboutGame;
    }

    public void setAboutGame(String aboutGame) {
        this.aboutGame = aboutGame;
    }

    private String web;

    @Column(name = "web")
    @Basic
    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    private String larpDb;

    @Column(name = "larp_db")
    @Basic
    public String getLarpDb() {
        return larpDb;
    }

    public void setLarpDb(String larpDb) {
        this.larpDb = larpDb;
    }

    private Timestamp registrationStartedDate;

    @Column(name = "registration_started")
    @Basic
    public Timestamp getRegistrationStartedDate() {
        return registrationStartedDate;
    }

    public void setRegistrationStartedDate(Timestamp registrationStartedDate) {
        this.registrationStartedDate = registrationStartedDate;
    }

    private String registrationStartedTime;

    @Transient
    public String getRegistrationStartedTime() {
        return registrationStartedTime;
    }

    public void setRegistrationStartedTime(String registrationStartedTime) {
        this.registrationStartedTime = registrationStartedTime;
    }


    private String ordinaryPlayerText;

    @Column(name = "ordinary_player_text")
    @Basic
    public String getOrdinaryPlayerText() {
        return ordinaryPlayerText;
    }

    public void setOrdinaryPlayerText(String ordinaryPlayerText) {
        this.ordinaryPlayerText = ordinaryPlayerText;
    }

    private String substitutesText;

    @Column(name = "replacements_text")
    @Basic
    public String getSubstitutesText() {
        return substitutesText;
    }

    public void setSubstitutesText(String replacementsText) {
        this.substitutesText = replacementsText;
    }

    private String registeredSubstitute;

    @Column(name = "registered_substitute")
    @Basic
    public String getRegisteredSubstitute() {
        return registeredSubstitute;
    }

    public void setRegisteredSubstitute(String registeredSubstitute) {
        this.registeredSubstitute = registeredSubstitute;
    }

    private String action;

    @Column(name = "action")
    @Basic
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    private Boolean mailProhibition;

    @Column(name = "mail_prohibition")
    @Basic
    public Boolean getMailProhibition() {
        return mailProhibition;
    }

    public void setMailProhibition(Boolean mailProhibition) {
        this.mailProhibition = mailProhibition;
    }

    private boolean paymentFinished;

    @Column(name = "payment_finished")
    @Basic
    public boolean getPaymentFinished() {
        return paymentFinished;
    }

    public void setPaymentFinished(boolean paymentFinished) {
        this.paymentFinished = paymentFinished;
    }

    private Map<Object, UserAttendedGame> gameEntities;

    @MapKey(name = "gameId")
    @OneToMany(mappedBy = "attendedGame")
    public Map<Object, UserAttendedGame> getGameEntities() {
        return gameEntities;
    }

    public void setGameEntities(Map<Object, UserAttendedGame> gameEntities) {
        this.gameEntities = gameEntities;
    }


    private Map<Object, UserIsEditor> editedByUsers;

    @MapKey(name = "gameId")
    @OneToMany(mappedBy = "editGame")
    @BatchSize(size = 2)
    public Map<Object, UserIsEditor> getEditedByUsers() {
        return editedByUsers;
    }

    public void setEditedByUsers(Map<Object, UserIsEditor> editedByUsers) {
        this.editedByUsers = editedByUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (addedBy != game.addedBy) return false;
        if (bothRole != game.bothRole) return false;
        if (confirmed != game.confirmed) return false;
        if (id != game.id) return false;
        if (menRole != game.menRole) return false;
        if (womenRole != game.womenRole) return false;
        if (aboutGame != null ? !aboutGame.equals(game.aboutGame) : game.aboutGame != null) return false;
        if (action != null ? !action.equals(game.action) : game.action != null) return false;
        if (anotation != null ? !anotation.equals(game.anotation) : game.anotation != null) return false;
        if (author != null ? !author.equals(game.author) : game.author != null) return false;
        if (date != null ? !date.equals(game.date) : game.date != null) return false;
        if (editedByUsers != null ? !editedByUsers.equals(game.editedByUsers) : game.editedByUsers != null)
            return false;
        if (gameEntities != null ? !gameEntities.equals(game.gameEntities) : game.gameEntities != null) return false;
        if (image != null ? !image.equals(game.image) : game.image != null) return false;
        if (info != null ? !info.equals(game.info) : game.info != null) return false;
        if (larpDb != null ? !larpDb.equals(game.larpDb) : game.larpDb != null) return false;
        if (mailProhibition != null ? !mailProhibition.equals(game.mailProhibition) : game.mailProhibition != null)
            return false;
        if (name != null ? !name.equals(game.name) : game.name != null) return false;
        if (ordinaryPlayerText != null ? !ordinaryPlayerText.equals(game.ordinaryPlayerText) : game.ordinaryPlayerText != null)
            return false;
        if (place != null ? !place.equals(game.place) : game.place != null) return false;
        if (registeredSubstitute != null ? !registeredSubstitute.equals(game.registeredSubstitute) : game.registeredSubstitute != null)
            return false;
        if (registrationStartedDate != null ? !registrationStartedDate.equals(game.registrationStartedDate) : game.registrationStartedDate != null)
            return false;
        if (registrationStartedTime != null ? !registrationStartedTime.equals(game.registrationStartedTime) : game.registrationStartedTime != null)
            return false;
        if (shortText != null ? !shortText.equals(game.shortText) : game.shortText != null) return false;
        if (substitutesText != null ? !substitutesText.equals(game.substitutesText) : game.substitutesText != null)
            return false;
        if (time != null ? !time.equals(game.time) : game.time != null) return false;
        if (web != null ? !web.equals(game.web) : game.web != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (anotation != null ? anotation.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (confirmed ? 1 : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + addedBy;
        result = 31 * result + menRole;
        result = 31 * result + womenRole;
        result = 31 * result + bothRole;
        result = 31 * result + (shortText != null ? shortText.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (aboutGame != null ? aboutGame.hashCode() : 0);
        result = 31 * result + (web != null ? web.hashCode() : 0);
        result = 31 * result + (larpDb != null ? larpDb.hashCode() : 0);
        result = 31 * result + (registrationStartedDate != null ? registrationStartedDate.hashCode() : 0);
        result = 31 * result + (registrationStartedTime != null ? registrationStartedTime.hashCode() : 0);
        result = 31 * result + (ordinaryPlayerText != null ? ordinaryPlayerText.hashCode() : 0);
        result = 31 * result + (substitutesText != null ? substitutesText.hashCode() : 0);
        result = 31 * result + (registeredSubstitute != null ? registeredSubstitute.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (mailProhibition != null ? mailProhibition.hashCode() : 0);
        result = 31 * result + (gameEntities != null ? gameEntities.hashCode() : 0);
        result = 31 * result + (editedByUsers != null ? editedByUsers.hashCode() : 0);
        return result;
    }
}
