package cz.hrajlarp.model;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    // number of remaining game roles (not assigned yet)
    private int menFreeRoles;
    private int womenFreeRoles;
    private int bothFreeRoles;

    private int menAssignedRoles;
    private int womenAssignedRoles;

    private boolean full;   // game is full (no free roles left)

    private List<HrajUserEntity> assignedUsers;


    private static final int MEN = 0;
    private static final int WOMEN = 1;
    private static final int BOTH = 2;
    private static final int ROLE_TYPES_CNT = 3;


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

    public void setSignedRolesCounts(List<HrajUserEntity> assignedUsers){

        this.assignedUsers = assignedUsers;

        int[] rolesAssigned = new int[ROLE_TYPES_CNT];
        Arrays.fill(rolesAssigned, 0);

        for (HrajUserEntity user: assignedUsers){
            if(user.getGender() == MEN)
                rolesAssigned[MEN]++;
            else
                rolesAssigned[WOMEN]++;

            if (getMenRole() < rolesAssigned[MEN])
                rolesAssigned[BOTH] += rolesAssigned[MEN] - getMenRole();
            if (getWomenRole() < rolesAssigned[WOMEN])
                rolesAssigned[BOTH] += rolesAssigned[WOMEN] - getWomenRole();
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

    public int getMenFreeRoles() {
        return menFreeRoles;
    }

    public int getWomenFreeRoles() {
        return womenFreeRoles;
    }

    public int getBothFreeRoles() {
        return bothFreeRoles;
    }

    public int getMenAssignedRoles() {
        return menAssignedRoles;
    }

    public int getWomenAssignedRoles() {
        return womenAssignedRoles;
    }

    public boolean isFull(){
        return this.full;
    }

    public void setFull(int gender){
        if (gender == MEN && getMenFreeRoles() > 0 && getBothFreeRoles() > 0) full = false;
        else full = !(gender == WOMEN && getWomenFreeRoles() > 0 && getBothFreeRoles() > 0);
    }

    public boolean isAvailableToUser(HrajUserEntity user){
        if(assignedUsers == null)
            return false; // unknown ! ASSIGNED USERS NOT SET YET

        if(assignedUsers.contains(user))
            return false; // user is already signed

        if(user.getGender() == MEN && getMenFreeRoles() > 0)
            return true; // user is a man and there are some free men roles

        if(user.getGender() == WOMEN && getWomenFreeRoles() > 0)
            return true; // user is a woman and there are some free women roles

        return (getBothFreeRoles() > 0); // user can still sign as undecided
    }
}
