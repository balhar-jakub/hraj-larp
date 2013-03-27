package cz.hrajlarp.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;



/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 6.3.13
 * Time: 23:12
 */
@Table(name = "game", schema = "public", catalog = "")
@Entity
public class GameEntity {
    private Integer id;

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_key_gen")
    @SequenceGenerator(name = "id_key_gen", sequenceName = "hraj_game_id_seq", allocationSize = 1)
    @Column(name = "id")
    @Id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    @Transient
    private String time;

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

    private String image;

    @Column(name = "image")
    @Basic
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private Integer addedBy;

    @Column(name = "added_by")
    @Basic
    public Integer getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(Integer addedBy) {
        this.addedBy = addedBy;
    }

    private Integer menRole;

    @Column(name = "men_role")
    @Basic
    public Integer getMenRole() {
        return menRole;
    }

    public void setMenRole(Integer menRole) {
        this.menRole = menRole;
    }

    private Integer womenRole;

    @Column(name = "women_role")
    @Basic
    public Integer getWomenRole() {
        return womenRole;
    }

    public void setWomenRole(Integer womenRole) {
        this.womenRole = womenRole;
    }

    private Integer bothRole;

    @Column(name = "both_role")
    @Basic
    public Integer getBothRole() {
        return bothRole;
    }

    public void setBothRole(Integer bothRole) {
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

    private Map<Object, UserAttendedGameEntity> gameEntities;

    @MapKey(name = "gameId")
    @OneToMany(mappedBy = "attendedGame")
    public Map<Object, UserAttendedGameEntity> getGameEntities() {
        return gameEntities;
    }

    public void setGameEntities(Map<Object, UserAttendedGameEntity> gameEntities) {
        this.gameEntities = gameEntities;
    }






    /* Attributes and methods taken from former Game.java class */

    // number of remaining game roles (not assigned yet)
    private int menFreeRoles;
    private int womenFreeRoles;
    private int bothFreeRoles;

    private int menAssignedRoles;
    private int womenAssignedRoles;

    private boolean full; // true if game has no role left

    private static final int MEN = 0;
    private static final int WOMEN = 1;
    private static final int BOTH = 2;
    private static final int ROLE_TYPES_CNT = 3;

    @Transient
    public boolean isFull(){
        return getFull();
    }

     @Transient
     public void setFull(int gender){
        if (gender == MEN && getMenFreeRoles() > 0 && getBothFreeRoles() > 0) full = false;
        else full = !(gender == WOMEN && getWomenFreeRoles() > 0 && getBothFreeRoles() > 0);
    }

    @Transient
    public String getDateAsDMY(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date);
    }

    @Transient
    public String getDateAsDM(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM");
        return sdf.format(date);
    }

    @Transient
    public String getDateAsDayName(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");    // day name
        return sdf.format(date);
    }

    @Transient
    public String getDateTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }

    @Transient
    public void setSignedRolesCounts(List assignedUsers){

        int[] rolesAssigned = new int[ROLE_TYPES_CNT];
        Arrays.fill(rolesAssigned, 0);

        for (Object o : assignedUsers){
            if(o instanceof HrajUserEntity){
                HrajUserEntity user = (HrajUserEntity) o;

                if(user.getGender() == MEN)
                    rolesAssigned[MEN]++;
                else
                    rolesAssigned[WOMEN]++;

                if(getMenRole() < rolesAssigned[MEN])
                    rolesAssigned[BOTH]+= rolesAssigned[MEN] - getMenRole();
                if(getWomenRole() < rolesAssigned[WOMEN])
                    rolesAssigned[BOTH]+= rolesAssigned[WOMEN] - getWomenRole();
            }
        }
        this.menAssignedRoles = rolesAssigned[MEN];
        this.womenAssignedRoles = rolesAssigned[WOMEN];

        if(menAssignedRoles > getMenRole())
            rolesAssigned[MEN] = getMenRole();
        if(womenAssignedRoles > getWomenRole())
            rolesAssigned[WOMEN] = getWomenRole();

        this.menFreeRoles = getMenRole() - rolesAssigned[MEN];
        this.womenFreeRoles = getWomenRole() - rolesAssigned[WOMEN];
        this.bothFreeRoles = getBothRole() - rolesAssigned[BOTH];

    }

    @Transient
    public int getMenFreeRoles() {
        return menFreeRoles;
    }

    @Transient
    public int getWomenFreeRoles() {
        return womenFreeRoles;
    }

    @Transient
    public int getBothFreeRoles() {
        return bothFreeRoles;
    }

    @Transient
    public int getMenAssignedRoles() {
        return menAssignedRoles;
    }

    @Transient
    public int getWomenAssignedRoles() {
        return womenAssignedRoles;
    }

    @Transient
    public boolean getFull(){
        return menFreeRoles == 0 && womenFreeRoles == 0 && bothFreeRoles == 0;
    }

    @Transient
    public boolean isAvailableToUser(HrajUserEntity user){
        return true;
    }
}
