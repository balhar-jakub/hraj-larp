package cz.hrajlarp.entity;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;


/**
 *
 */
@Table(name = "game", schema = "public")
@Entity
public class Game {
    private Integer id;
    private Timestamp date;
    private Timestamp registrationStartedDate;

    private String name;
    private String anotation;
    private String author;
    private String imagePath;
    private String shortText;
    private String place;
    private String info;
    private String aboutGame;
    private String web;
    private String larpDb;
    private String ordinaryPlayerText;
    private String substitutesText;
    private String registeredSubstitute;
    private String action;

    private int bothRole;
    private int menRole;
    private int womenRole;

    private boolean confirmed;
    private boolean mailProhibition;
    private boolean paymentFinished;

    private HrajUser addedBy;
    private List<UserAttendedGame> players;
    private List<HrajUser> editors;

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

    @Column(name = "name")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "date")
    @Basic
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Column(name = "anotation")
    @Basic
    public String getAnotation() {
        return anotation;
    }

    public void setAnotation(String anotation) {
        this.anotation = anotation;
    }

    @Column(name = "author")
    @Basic
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Column(name = "confirmed")
    @Basic
    public boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Column(name = "image")
    @Basic
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Column(name = "men_role")
    @Basic
    public int getMenRole() {
        return menRole;
    }

    public void setMenRole(int menRole) {
        this.menRole = menRole;
    }

    @Column(name = "women_role")
    @Basic
    public int getWomenRole() {
        return womenRole;
    }

    public void setWomenRole(int womenRole) {
        this.womenRole = womenRole;
    }

    @Column(name = "both_role")
    @Basic
    public int getBothRole() {
        return bothRole;
    }

    public void setBothRole(int bothRole) {
        this.bothRole = bothRole;
    }

    @Column(name = "short_text")
    @Basic
    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    @Column(name = "place")
    @Basic
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Column(name = "info")
    @Basic
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Column(name = "about_game")
    @Basic
    public String getAboutGame() {
        return aboutGame;
    }

    public void setAboutGame(String aboutGame) {
        this.aboutGame = aboutGame;
    }

    @Column(name = "web")
    @Basic
    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    @Column(name = "larp_db")
    @Basic
    public String getLarpDb() {
        return larpDb;
    }

    public void setLarpDb(String larpDb) {
        this.larpDb = larpDb;
    }

    @Column(name = "registration_started")
    @Basic
    public Timestamp getRegistrationStartedDate() {
        return registrationStartedDate;
    }

    public void setRegistrationStartedDate(Timestamp registrationStartedDate) {
        this.registrationStartedDate = registrationStartedDate;
    }

    @Column(name = "ordinary_player_text")
    @Basic
    public String getOrdinaryPlayerText() {
        return ordinaryPlayerText;
    }

    public void setOrdinaryPlayerText(String ordinaryPlayerText) {
        this.ordinaryPlayerText = ordinaryPlayerText;
    }


    @Column(name = "replacements_text")
    @Basic
    public String getSubstitutesText() {
        return substitutesText;
    }

    public void setSubstitutesText(String replacementsText) {
        this.substitutesText = replacementsText;
    }

    @Column(name = "registered_substitute")
    @Basic
    public String getRegisteredSubstitute() {
        return registeredSubstitute;
    }

    public void setRegisteredSubstitute(String registeredSubstitute) {
        this.registeredSubstitute = registeredSubstitute;
    }

    @Column(name = "action")
    @Basic
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
    @Column(name = "mail_prohibition")
    @Basic
    public boolean getMailProhibition() {
        return mailProhibition;
    }

    public void setMailProhibition(boolean mailProhibition) {
        this.mailProhibition = mailProhibition;
    }

    @Column(name = "payment_finished")
    @Basic
    public boolean getPaymentFinished() {
        return paymentFinished;
    }

    public void setPaymentFinished(boolean paymentFinished) {
        this.paymentFinished = paymentFinished;
    }

    @ManyToOne
    @JoinColumn(name = "added_by", referencedColumnName = "id", nullable = false, insertable=true, updatable=false)
    public HrajUser getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(HrajUser addedBy) {
        this.addedBy = addedBy;
    }

    @MapKey(name = "gameId")
    @OneToMany(mappedBy = "attendedGame")
    public List<UserAttendedGame> getPlayers() {
        return players;
    }

    public void setPlayers(List<UserAttendedGame> players) {
        this.players = players;
    }


    @ManyToMany
    @JoinTable(name="user_is_editor",
            joinColumns = {@JoinColumn(name="game_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name="user_id", referencedColumnName = "id")}
    )
    public List<HrajUser> getEditors() {
        return editors;
    }

    public void setEditors(List<HrajUser> editors) {
        this.editors = editors;
    }
}
