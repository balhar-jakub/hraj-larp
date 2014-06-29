package cz.hrajlarp.dto;

/**
 * Created by jbalhar on 6/29/2014.
 */
public class PlayerDto {
    private int id;
    private String userName;
    private String fullName;
    private String phone;
    private String email;
    private String gender;
    private String payedText;

    private boolean automatic;
    private boolean payed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPayedText() {
        return payedText;
    }

    public void setPayedText(String payedText) {
        this.payedText = payedText;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }
}
