package cz.hrajlarp.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 6.3.13
 * Time: 23:12
 */
@javax.persistence.Table(name = "game", schema = "public", catalog = "")
@Entity
public class GameEntity {
    private Integer id;

    @javax.persistence.Column(name = "id")
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String name;

    @javax.persistence.Column(name = "name")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Timestamp date;

    @javax.persistence.Column(name = "date")
    @Basic
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    private String anotation;

    @javax.persistence.Column(name = "anotation")
    @Basic
    public String getAnotation() {
        return anotation;
    }

    public void setAnotation(String anotation) {
        this.anotation = anotation;
    }

    private String author;

    @javax.persistence.Column(name = "author")
    @Basic
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private String image;

    @javax.persistence.Column(name = "image")
    @Basic
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private Integer addedBy;

    @javax.persistence.Column(name = "added_by")
    @Basic
    public Integer getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Integer addedBy) {
        this.addedBy = addedBy;
    }

    private Integer menRole;

    @javax.persistence.Column(name = "men_role")
    @Basic
    public Integer getMenRole() {
        return menRole;
    }

    public void setMenRole(Integer menRole) {
        this.menRole = menRole;
    }

    private Integer womenRole;

    @javax.persistence.Column(name = "women_role")
    @Basic
    public Integer getWomenRole() {
        return womenRole;
    }

    public void setWomenRole(Integer womenRole) {
        this.womenRole = womenRole;
    }

    private Integer bothRole;

    @javax.persistence.Column(name = "both_role")
    @Basic
    public Integer getBothRole() {
        return bothRole;
    }

    public void setBothRole(Integer bothRole) {
        this.bothRole = bothRole;
    }

    private String shortText;

    @javax.persistence.Column(name = "short_text")
    @Basic
    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    private String place;

    @javax.persistence.Column(name = "place")
    @Basic
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    private String info;

    @javax.persistence.Column(name = "info")
    @Basic
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private String aboutGame;

    @javax.persistence.Column(name = "about_game")
    @Basic
    public String getAboutGame() {
        return aboutGame;
    }

    public void setAboutGame(String aboutGame) {
        this.aboutGame = aboutGame;
    }

    private String web;

    @javax.persistence.Column(name = "web")
    @Basic
    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    private String larpDb;

    @javax.persistence.Column(name = "larp_db")
    @Basic
    public String getLarpDb() {
        return larpDb;
    }

    public void setLarpDb(String larpDb) {
        this.larpDb = larpDb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameEntity that = (GameEntity) o;

        if (aboutGame != null ? !aboutGame.equals(that.aboutGame) : that.aboutGame != null) return false;
        if (addedBy != null ? !addedBy.equals(that.addedBy) : that.addedBy != null) return false;
        if (anotation != null ? !anotation.equals(that.anotation) : that.anotation != null) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        if (bothRole != null ? !bothRole.equals(that.bothRole) : that.bothRole != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        if (info != null ? !info.equals(that.info) : that.info != null) return false;
        if (larpDb != null ? !larpDb.equals(that.larpDb) : that.larpDb != null) return false;
        if (menRole != null ? !menRole.equals(that.menRole) : that.menRole != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (place != null ? !place.equals(that.place) : that.place != null) return false;
        if (shortText != null ? !shortText.equals(that.shortText) : that.shortText != null) return false;
        if (web != null ? !web.equals(that.web) : that.web != null) return false;
        if (womenRole != null ? !womenRole.equals(that.womenRole) : that.womenRole != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (anotation != null ? anotation.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (addedBy != null ? addedBy.hashCode() : 0);
        result = 31 * result + (menRole != null ? menRole.hashCode() : 0);
        result = 31 * result + (womenRole != null ? womenRole.hashCode() : 0);
        result = 31 * result + (bothRole != null ? bothRole.hashCode() : 0);
        result = 31 * result + (shortText != null ? shortText.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (aboutGame != null ? aboutGame.hashCode() : 0);
        result = 31 * result + (web != null ? web.hashCode() : 0);
        result = 31 * result + (larpDb != null ? larpDb.hashCode() : 0);
        return result;
    }
}
