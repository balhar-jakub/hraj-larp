package cz.hrajlarp.model.entity;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 6.3.13
 * Time: 23:12
 */
@Table(name = "hraj_user", schema = "public", catalog = "")
@Entity
public class HrajUserEntity {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HrajUserEntity that = (HrajUserEntity) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (gender != null ? !gender.equals(that.gender) : that.gender != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (mailInformation != null ? !mailInformation.equals(that.mailInformation) : that.mailInformation != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (activationLink != null ? !activationLink.equals(that.activationLink) : that.activationLink != null) return false;
        if (activated != null ? !activated.equals(that.activated) : that.activated != null) return false;

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
        result = 31 * result + (activationLink != null ? activationLink.hashCode() : 0);
        result = 31 * result + (activated != null ? activated.hashCode() : 0);
        return result;
    }

    private String genderForm;
    public void setGenderForm(String genderForm) {
        this.genderForm = genderForm;
    }

    @Transient
    public String getGenderForm(){
        return genderForm;
    }

    private String passwordAgain;
    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }

    @Transient
    public String getPasswordAgain(){
        return passwordAgain;
    }
    
    private String oldPassword;
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    @Transient
    public String getOldPassword(){
        return oldPassword;
    }

    private String genderTextual = null;

    @Transient
    public String getGenderTextual() {
        if(genderTextual == null) {
            if(getGender() == 0){
                setGenderTextual("Muž");
            } else {
                setGenderTextual("Žena");
            }
        }
        return genderTextual;
    }

    public void setGenderTextual(String genderTextual) {
        this.genderTextual = genderTextual;
    }


    private Map<Object, UserAttendedGameEntity> userEntities;

    @MapKey(name = "userId")
    @OneToMany(mappedBy = "userAttended")
    public Map<Object, UserAttendedGameEntity> getUserEntities() {
        return userEntities;
    }


    public void setUserEntities(Map<Object, UserAttendedGameEntity> userEntities) {
        this.userEntities = userEntities;
    }


    private Map<Object, UserIsEditorEntity> editingGames;

    @MapKey(name = "userId")
    @OneToMany(mappedBy = "userEdit")
    public Map<Object, UserIsEditorEntity> getEditingGames() {
        return editingGames;
    }

    public void setEditingGames(Map<Object, UserIsEditorEntity> editingGames) {
        this.editingGames = editingGames;
    }
}
