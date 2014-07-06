package cz.hrajlarp.dto;

/**
 * Created by jbalhar on 7/6/2014.
 */
public class PlayerSpacesDto {
    private int menRoles;
    private int womenRoles;
    private int bothRoles;

    private int menFreeRoles;
    private int womenFreeRoles;
    private int bothFreeRoles;

    private int menSubstitutes;
    private int womenSubstitutes;

    public int getMenRoles() {
        return menRoles;
    }

    public void setMenRoles(int menRoles) {
        this.menRoles = menRoles;
    }

    public int getWomenRoles() {
        return womenRoles;
    }

    public void setWomenRoles(int womenRoles) {
        this.womenRoles = womenRoles;
    }

    public int getBothRoles() {
        return bothRoles;
    }

    public void setBothRoles(int bothRoles) {
        this.bothRoles = bothRoles;
    }

    public int getMenFreeRoles() {
        return menFreeRoles;
    }

    public void setMenFreeRoles(int menFreeRoles) {
        this.menFreeRoles = menFreeRoles;
    }

    public int getWomenFreeRoles() {
        return womenFreeRoles;
    }

    public void setWomenFreeRoles(int womenFreeRoles) {
        this.womenFreeRoles = womenFreeRoles;
    }

    public int getBothFreeRoles() {
        return bothFreeRoles;
    }

    public void setBothFreeRoles(int bothFreeRoles) {
        this.bothFreeRoles = bothFreeRoles;
    }

    public int getMenSubstitutes() {
        return menSubstitutes;
    }

    public void setMenSubstitutes(int menSubstitutes) {
        this.menSubstitutes = menSubstitutes;
    }

    public int getWomenSubstitutes() {
        return womenSubstitutes;
    }

    public void setWomenSubstitutes(int womenSubstitutes) {
        this.womenSubstitutes = womenSubstitutes;
    }
}
