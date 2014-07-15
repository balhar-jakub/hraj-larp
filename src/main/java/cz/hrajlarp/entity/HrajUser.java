package cz.hrajlarp.entity;

import cz.hrajlarp.enums.Gender;
import cz.hrajlarp.enums.HrajRoles;

import javax.persistence.*;
import java.util.List;

/**
 *
 */
@Table(name = "hraj_user", schema = "public")
@Entity
public class HrajUser {
    private Integer id;
    private String name;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String activationLink;

    private boolean mailInformation;
    private boolean activated;
    private boolean placeFinder;
    private boolean scheduler;
    private boolean accountant;

    private Gender gender;
    private List<UserRoles> roles;


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

    @Column(name = "name")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "last_name")
    @Basic
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "user_name")
    @Basic
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "password")
    @Basic
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "email")
    @Basic
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "phone")
    @Basic
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "mail_information")
    @Basic
    public boolean getMailInformation() {
        return mailInformation;
    }

    public void setMailInformation(boolean mailInformation) {
        this.mailInformation = mailInformation;
    }
    
    @Column(name = "activated")
    @Basic
    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    @Column(name = "activation_link")
    @Basic
    public String getActivationLink() {
        return activationLink;
    }

    public void setActivationLink(String activationLink) {
        this.activationLink = activationLink;
    }

    @Column(name = "gender")
    @Enumerated(EnumType.ORDINAL)
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @OneToMany(mappedBy = "user")
    public List<UserRoles> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoles> roles) {
        this.roles = roles;
    }


    @Column(name="accountant")
    @Basic
    public boolean isAccountant() {
        return accountant;
    }

    public void setAccountant(boolean accountant) {
        this.accountant = accountant;
    }

    @Column(name="place_finder")
    @Basic
    public boolean isPlaceFinder() {
        return placeFinder;
    }

    public void setPlaceFinder(boolean placeFinder) {
        this.placeFinder = placeFinder;
    }

    @Column(name ="scheduler")
    @Basic
    public boolean isScheduler() {
        return scheduler;
    }

    public void setScheduler(boolean scheduler) {
        this.scheduler = scheduler;
    }

    private List<UserAttendedGame> attendedGames;

    @OneToMany(mappedBy = "user")
    public List<UserAttendedGame> getAttendedGames() {
        return attendedGames;
    }

    public void setAttendedGames(List<UserAttendedGame> attendedGames) {
        this.attendedGames = attendedGames;
    }
}
