package cz.hrajlarp.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 6.3.13
 * Time: 23:12
 */
@javax.persistence.Table(name = "hraj_user", schema = "public", catalog = "")
@Entity
public class HrajUserEntity {
	
    private Integer id;
    
    @SequenceGenerator(name="SequenceIdGenerator",sequenceName="hraj_user_id_seq", allocationSize=1)
    @javax.persistence.Column(name = "id")
    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "SequenceIdGenerator")//strategy=GenerationType.AUTO)
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
    
    private String lastName;

    @javax.persistence.Column(name = "last_name")
    @Basic
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String userName;

    @javax.persistence.Column(name = "user_name")
    @Basic
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String password;

    @javax.persistence.Column(name = "password")
    @Basic
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    
    private String passwordAgain;
    @Transient
    public String getPasswordAgain() {
        return passwordAgain;
    }
    
    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }
    
    private String email;

    @javax.persistence.Column(name = "email")
    @Basic
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    private String phone;

    @javax.persistence.Column(name = "phone")
    @Basic
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private Integer gender;//0=male; 1=female

    @javax.persistence.Column(name = "gender")
    @Basic
    public Integer getGender() {
        return gender;
    }
    
    public void setGender(Integer gender) {
        this.gender = gender;
    }
    
    
    private String genderForm;
    @Transient
    public String getGenderForm() {
        return genderForm;
    }
    
    public void setGenderForm(String genderForm) {
        this.genderForm = genderForm;
    }

    private Boolean mailInformation;

    @javax.persistence.Column(name = "mail_information")
    @Basic
    public Boolean getMailInformation() {
        return mailInformation;
    }

    public void setMailInformation(Boolean mailInformation) {
        this.mailInformation = mailInformation;
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
        return result;
    }
}
