package cz.hrajlarp.model;

import cz.hrajlarp.exceptions.DuplicatePlayerException;
import cz.hrajlarp.exceptions.TooManyPlayersException;
import cz.hrajlarp.utils.DateUtils;
import cz.hrajlarp.utils.MailService;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;


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

    private Boolean confirmed;

    @Column(name = "confirmed")
    @Basic
    public Boolean getConfirmed() {
        return confirmed != null && confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
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

    private String replacementsText;

    @Column(name = "replacements_text")
    @Basic
    public String getReplacementsText() {
        return replacementsText;
    }

    public void setReplacementsText(String replacementsText) {
        this.replacementsText = replacementsText;
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
        if (registrationStartedDate != null ? !registrationStartedDate.equals(that.registrationStartedDate) : that.registrationStartedDate != null) return false;
        if (ordinaryPlayerText != null ? !ordinaryPlayerText.equals(that.ordinaryPlayerText) : that.ordinaryPlayerText != null) return false;
        if (replacementsText != null ? !replacementsText.equals(that.replacementsText) : that.replacementsText != null) return false;
        if (action != null ? !action.equals(that.action) : that.action != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (registrationStartedTime != null ? !registrationStartedTime.equals(that.registrationStartedTime) : that.registrationStartedTime != null) return false;
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
        result = 31 * result + (registrationStartedDate != null ? registrationStartedDate.hashCode() : 0);
        result = 31 * result + (ordinaryPlayerText != null ? ordinaryPlayerText.hashCode() : 0);
        result = 31 * result + (replacementsText != null ? replacementsText.hashCode() : 0);
        result = 31 * result + (action != null ? action.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (registrationStartedTime != null ? registrationStartedTime.hashCode() : 0);

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


    private Map<Object, UserIsEditorEntity> editedByUsers;

    @MapKey(name = "gameId")
    @OneToMany(mappedBy = "editGame")
    public Map<Object, UserIsEditorEntity> getEditedByUsers() {
        return editedByUsers;
    }

    public void setEditedByUsers(Map<Object, UserIsEditorEntity> editedByUsers) {
        this.editedByUsers = editedByUsers;
    }


    @Transient
    public String getDateAsDMY() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(date);
    }

    @Transient
    public String getDateAsYMD() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    @Transient
    public String getDateAsYMDHM() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    @Transient
    public String getDateAsDM() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM");
        return sdf.format(date);
    }

    @Transient
    public String getDateAsDayName() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");    // day name
        return sdf.format(date);
    }

    @Transient
    public String getTimeAsHM(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(getDate());
    }

    @Transient
    public String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }

    private boolean festival;

    @Transient
    public boolean isFestival() {
        festival = shortText.equals("Festivalová");
        return festival;
    }

    @Transient
    public String getRegistrationStartedDMYHM() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return sdf.format(getRegistrationStartedDate());
    }

    @Transient
    private int menFreeRoles;
    @Transient
    private int womenFreeRoles;
    @Transient
    private int bothFreeRoles;

    @Transient
    private int menSubstitutes;
    @Transient
    private int womenSubstitutes;

    /**
     * This method counts and assigns amount of free roles as well as amount of substitutes
     * on gmae of every gender.
     *
     * @param userAttendedGameDAO Dao which can retrieve users for game
     */
    @Transient
    public void countPlayers(UserAttendedGameDAO userAttendedGameDAO) throws Exception {
        List<HrajUserEntity> assignedUsers = userAttendedGameDAO.getUsersByGameIdNoSubstitutes(getId());
        List<HrajUserEntity> substitutes = userAttendedGameDAO.getSubstituteUsersByGameId(getId());

        countPlayers(assignedUsers,substitutes);
    }

    @Transient
    protected void countPlayers(List<HrajUserEntity> assignedUsers,
                                List<HrajUserEntity> substitutes) throws DuplicatePlayerException, TooManyPlayersException {
        testForDuplicates(assignedUsers, substitutes);
        countFreeRoles(assignedUsers);
        countSubstitutes(assignedUsers, substitutes);
        testRolesforTooMany();
    }

    @Transient
    private void countSubstitutes(List<HrajUserEntity> assignedUsers, List<HrajUserEntity> substitutes) throws DuplicatePlayerException {
        if (substitutes != null && !substitutes.isEmpty()) {
            for (HrajUserEntity user : substitutes) {
                if (assignedUsers.contains(user)) {
                    // user is signed to this game as regular and substitute at the same time
                    // should not happen (user and game makes primary key in UserAttendedGame)
                    throw new DuplicatePlayerException("GameEntity error: assigned user (id=" + user.getId()
                            + ") is also a substitute on one game (id=" + getId() + ")");
                }

                if (user.getGender() == Gender.MEN.ordinal())
                    setMenSubstitutes(getMenSubstitutes() + 1);
                else
                    setWomenSubstitutes(getWomenSubstitutes() + 1);
            }
        }
    }

    @Transient
    private void countFreeRoles(List<HrajUserEntity> assignedUsers) {
        int genderTypes = 3;
        int[] rolesAssigned = new int[genderTypes];
        if (assignedUsers != null && !assignedUsers.isEmpty()) {
            for (HrajUserEntity user : assignedUsers) {
                if (user.getGender() == Gender.MEN.ordinal())
                    rolesAssigned[Gender.MEN.ordinal()]++;
                else
                    rolesAssigned[Gender.WOMEN.ordinal()]++;
            }
        }

        if (rolesAssigned[Gender.MEN.ordinal()] > menRole) {
            rolesAssigned[Gender.BOTH.ordinal()] += rolesAssigned[Gender.MEN.ordinal()] - menRole;
            rolesAssigned[Gender.MEN.ordinal()] = menRole;
        }
        if (rolesAssigned[Gender.WOMEN.ordinal()] > womenRole) {
            rolesAssigned[Gender.BOTH.ordinal()] += rolesAssigned[Gender.WOMEN.ordinal()] - womenRole;
            rolesAssigned[Gender.WOMEN.ordinal()] = womenRole;
        }

        // now rolesAssigned[] contains real counts of assigned
        // roles for MEN, WOMEN and BOTH (not only MEN and WOMEN)

        menFreeRoles = menRole - rolesAssigned[Gender.MEN.ordinal()];
        womenFreeRoles = womenRole - rolesAssigned[Gender.WOMEN.ordinal()];
        bothFreeRoles = bothRole - rolesAssigned[Gender.BOTH.ordinal()];
        if(bothFreeRoles < 0) {
            bothFreeRoles = 0;
        }
    }

    @Transient
    private boolean isDuplicate(List<HrajUserEntity> list, Set<HrajUserEntity> set) {
        return list.size() != set.size();
    }

    /**
     * It tests for duplicates, whether there is any user signed twice as regular player
     * or signed twice as a substitute.
     *
     * @param pAssignedUsers Regular players of the game
     * @param pSubstitutes Substitutes for the game
     * @throws DuplicatePlayerException Duplicate players
     */
    @Transient
    private void testForDuplicates(List<HrajUserEntity> pAssignedUsers,
                                   List<HrajUserEntity> pSubstitutes) throws DuplicatePlayerException {
        Set<HrajUserEntity> assignedUsers = new HashSet<HrajUserEntity>(pAssignedUsers);
        Set<HrajUserEntity> substitutes = new HashSet<HrajUserEntity>(pSubstitutes);
        // This should never happen
        if (isDuplicate(pAssignedUsers, assignedUsers)) {
            throw new DuplicatePlayerException("GameEntity error: assigned user is signed twice " +
                    "on one game (id=" + getId() + ")");
        }
        if (isDuplicate(pSubstitutes, substitutes)) {
            throw new DuplicatePlayerException("GameEntity error: assigned user is a substitute twice " +
                    "on one game (id=" + getId() + ")");
        }
    }

    /**
     * It tests for conditions related to too many players logged on one game.
     * These conditions should never happen.
     *
     * @throws cz.hrajlarp.exceptions.TooManyPlayersException
     */
    @Transient
    private void testRolesforTooMany() throws TooManyPlayersException {
        if (menFreeRoles < 0 || womenFreeRoles < 0 || bothFreeRoles < 0) {
            // the number of assigned players is bigger than the number of game roles
            int assigned = getMenFreeRoles() + getWomenFreeRoles() + getBothFreeRoles();
            throw new TooManyPlayersException("GameEntity error: number of assigned players (" + assigned +
                    ") is above limit of game roles (game id=" + getId() + ")");
        }

        if ((menFreeRoles > 0 || womenFreeRoles > 0) &&
                (bothFreeRoles != getBothRole())) {
            // The game is not full and has substitutes
            throw new TooManyPlayersException("GameEntity error: game has substitutes even " +
                    "though it is not full yet");
        }
    }

    /**
     * Method decides whether given user can sign up for this game or not.
     *
     *
     * @param user tested user
     * @return true, if user is not signed up for the game yet and the capacity
     *         has not been filled up yet (considering gender)
     *         false is returned even if list of assigned users has not been set yet!
     */
    @Transient
    public boolean isAvailableToUser(UserAttendedGameDAO userAttendedGameDAO,
                                     HrajUserEntity user) {
        if (userAttendedGameDAO.isLogged(getId(), user.getId()))
            return false; // user is already signed

        if (user.getGender() == Gender.MEN.ordinal() && getMenFreeRoles() > 0)
            return true; // user is a man and there are some free men roles

        if (user.getGender() == Gender.WOMEN.ordinal() && getWomenFreeRoles() > 0)
            return true; // user is a woman and there are some free women roles

        return (getBothFreeRoles() > 0); // user can still sign as undecided
    }

    /**
     * If any player becomes regular player, this sends email to him.
     *
     * @param mailService Serivce for sending the email.
     * @param userAttendedGameDAO Dao needed for finding if user is now regular
     * @param oldUser
     */
    @Transient
    public void logoutAndMailNewRegularPlayer (
            MailService mailService,
            UserAttendedGameDAO userAttendedGameDAO,
            HrajUserEntity oldUser) {
        boolean wasSubstitute = userAttendedGameDAO.isSubstitute(getId(), oldUser.getId());
        logOutUserFromGame(userAttendedGameDAO, oldUser.getId());

        try {
            countPlayers(userAttendedGameDAO);   //count new free roles count
        } catch (Exception e) {
            e.printStackTrace();
            /* TODO handle error and fix data in the database */
        }

        /* if logged out user was not just a substitute, some of pSubstitutes should replace him */
        if (!wasSubstitute) {
            UserAttendedGameEntity player = userAttendedGameDAO.
                    getFirstSubstitutedUAG(getId(), oldUser.getGender());  //get first substitute according to gender
            if (player != null) {
                player.setSubstitute(false);
                userAttendedGameDAO.editUserAttendedGame(player);             //edit this substitute as ordinary player

                player.notifyChangedByMail(mailService);
            }
        }
    }

    /**
     * It gets all players actually assigned to the game. Decides who is regular and who
     * is substitute and send mail to those whose status changed.
     *
     * @param userAttendedGameDAO Way to find players of game
     * @param mailService Service needed for sending mail
     */
    @Transient
    public void rerollLoggedUsers(UserAttendedGameDAO userAttendedGameDAO,
                                  MailService mailService) {
        List<UserAttendedGameEntity> allPlayers = userAttendedGameDAO.getAllPlayersOfGame(getId());

        int menRemaining = getMenRole();
        int womenRemaining = getWomenRole();
        int bothRemaining = getBothRole();
        for(UserAttendedGameEntity player: allPlayers) {
            if(player.getUserAttended().getGender() == Gender.MEN.ordinal()) {
                menRemaining--;
            } else {
                womenRemaining--;
            }

            if(menRemaining < 0){
                bothRemaining--;
                menRemaining = 0;
            }
            if(womenRemaining < 0){
                bothRemaining--;
                womenRemaining = 0;
            }

            if(bothRemaining < 0) {
                if(!player.isSubstitute()){
                    player.setSubstitute(true);
                    player.notifyChangedByMail(mailService);
                }
            } else {
                if(player.isSubstitute()) {
                    player.setSubstitute(false);
                    player.notifyChangedByMail(mailService);
                }
            }
        }
    }

    /**
     * It logs given player to the game and sends mail to him. Mail is chosen
     * depending on whether he is a regular player or a substitute.
     *
     *
     * @param userAttendedGameDAO
     * @param mailService
     * @param user
     * @throws Exception
     */
    @Transient
    public void loginAndMailPlayer(UserAttendedGameDAO userAttendedGameDAO,
                                   MailService mailService,
                                   HrajUserEntity user) throws Exception {
        UserAttendedGameEntity uage = new UserAttendedGameEntity();
        uage.setGameId(getId());
        uage.setUserId(user.getId());
        if (!userAttendedGameDAO.isLogged(uage)) {  //user is not logged in this game
            countPlayers(userAttendedGameDAO);   //count new free roles count

            if (isAvailableToUser(userAttendedGameDAO, user)) {
                uage.setSubstitute(false);
            }
            else {
                uage.setSubstitute(true);
            }
            Long variableSymbol = userAttendedGameDAO.getNextVariableSymbol();
            uage.setVariableSymbol(variableSymbol.toString());
            userAttendedGameDAO.addUserAttendedGame(uage);
            uage = userAttendedGameDAO.getLogged(getId(), user.getId());
            uage.notifyByMail(mailService);
        }
    }

    @Transient
    public boolean isInFuture(){
        Date gameDate = DateUtils.stringsToDate(getDate().toString(), getTime());
        return gameDate.after(new Date());
    }

    /**
     * This method takes care of logging user out of the game.
     *
     * @param userAttendedGameDAO dao used in logging
     * @param userId Id of the user to logout
     */
    @Transient
    private void logOutUserFromGame(UserAttendedGameDAO userAttendedGameDAO, int userId) {
        UserAttendedGameEntity uage = new UserAttendedGameEntity();
        uage.setGameId(getId());
        uage.setUserId(userId);
        userAttendedGameDAO.deleteUserAttendedGame(uage);
    }

    @Transient
    public boolean differsInPlayers(GameEntity toCompare){
        if(toCompare.getMenRole() != getMenRole() ||
                toCompare.getWomenRole() != getWomenRole() ||
                toCompare.getBothRole() != getBothRole()){
            return true;
        }
        return false;
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
    public int getWomenSubstitutes() {
        return womenSubstitutes;
    }

    @Transient
    public void setWomenSubstitutes(int womenSubstitutes) {
        this.womenSubstitutes = womenSubstitutes;
    }

    @Transient
    public boolean registrationStartsInFuture() {
        return registrationStartedDate.after(new Date());
    }

    @Transient
    public int getMenSubstitutes() {
        return menSubstitutes;
    }

    @Transient
    public void setMenSubstitutes(int menSubstitutes) {
        this.menSubstitutes = menSubstitutes;
    }
}
