package cz.hrajlarp.entity;

import cz.hrajlarp.enums.HrajRoles;

import javax.persistence.*;
import java.util.Map;

/**
 *
 */
@SuppressWarnings("RedundantIfStatement")
@Table(name = "hraj_user", schema = "public")
@Entity
public class HrajUser {
    private Integer id;

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_key_gen")
    @SequenceGenerator(name = "id_key_gen", sequenceName = "hraj_user_id_seq", allocationSize = 1)
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

    private String lastName;

    @Column(name = "last_name")
    @Basic
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String userName;

    @Column(name = "user_name")
    @Basic
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String password;

    @Column(name = "password")
    @Basic
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String email;

    @Column(name = "email")
    @Basic
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String phone;

    @Column(name = "phone")
    @Basic
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private Integer gender;

    @Column(name = "gender")
    @Basic
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    private Boolean mailInformation;

    @Column(name = "mail_information")
    @Basic
    public Boolean getMailInformation() {
        return mailInformation;
    }

    public void setMailInformation(Boolean mailInformation) {
        this.mailInformation = mailInformation;
    }
    
    private Boolean activated;

    @Column(name = "activated")
    @Basic
    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }
    
    private String activationLink;

    @Column(name = "activation_link")
    @Basic
    public String getActivationLink() {
        return activationLink;
    }

    public void setActivationLink(String activationLink) {
        this.activationLink = activationLink;
    }


    private HrajRoles role;

    @Enumerated(EnumType.STRING)
    @Column(name ="role")
    public HrajRoles getRole(){
        return role;
    }

    public void setRole(HrajRoles role) {
        this.role = role;
    }

    private boolean accountant;

    @Column(name="accountant")
    @Basic
    public boolean isAccountant() {
        return accountant;
    }

    public void setAccountant(boolean accountant) {
        this.accountant = accountant;
    }

    private boolean placeFinder;

    @Column(name="place_finder")
    @Basic
    public boolean isPlaceFinder() {
        return placeFinder;
    }

    public void setPlaceFinder(boolean placeFinder) {
        this.placeFinder = placeFinder;
    }

    private boolean scheduler;

    @Column(name ="scheduler")
    @Basic
    public boolean isScheduler() {
        return scheduler;
    }

    public void setScheduler(boolean scheduler) {
        this.scheduler = scheduler;
    }

    private Map<Object, UserAttendedGame> userEntities;

    @MapKey(name = "userId")
    @OneToMany(mappedBy = "userAttended")
    public Map<Object, UserAttendedGame> getUserEntities() {
        return userEntities;
    }


    public void setUserEntities(Map<Object, UserAttendedGame> userEntities) {
        this.userEntities = userEntities;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HrajUser hrajUser = (HrajUser) o;

        if (accountant != hrajUser.accountant) return false;
        if (placeFinder != hrajUser.placeFinder) return false;
        if (scheduler != hrajUser.scheduler) return false;
        if (activated != null ? !activated.equals(hrajUser.activated) : hrajUser.activated != null) return false;
        if (activationLink != null ? !activationLink.equals(hrajUser.activationLink) : hrajUser.activationLink != null)
            return false;
        if (email != null ? !email.equals(hrajUser.email) : hrajUser.email != null) return false;
        if (gender != null ? !gender.equals(hrajUser.gender) : hrajUser.gender != null) return false;
        if (id != null ? !id.equals(hrajUser.id) : hrajUser.id != null) return false;
        if (lastName != null ? !lastName.equals(hrajUser.lastName) : hrajUser.lastName != null) return false;
        if (mailInformation != null ? !mailInformation.equals(hrajUser.mailInformation) : hrajUser.mailInformation != null)
            return false;
        if (name != null ? !name.equals(hrajUser.name) : hrajUser.name != null) return false;
        if (password != null ? !password.equals(hrajUser.password) : hrajUser.password != null) return false;
        if (phone != null ? !phone.equals(hrajUser.phone) : hrajUser.phone != null) return false;
        if (role != hrajUser.role) return false;
        if (userEntities != null ? !userEntities.equals(hrajUser.userEntities) : hrajUser.userEntities != null)
            return false;
        if (userName != null ? !userName.equals(hrajUser.userName) : hrajUser.userName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (mailInformation != null ? mailInformation.hashCode() : 0);
        result = 31 * result + (activated != null ? activated.hashCode() : 0);
        result = 31 * result + (activationLink != null ? activationLink.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (accountant ? 1 : 0);
        result = 31 * result + (placeFinder ? 1 : 0);
        result = 31 * result + (scheduler ? 1 : 0);
        result = 31 * result + (userEntities != null ? userEntities.hashCode() : 0);
        return result;
    }
}
