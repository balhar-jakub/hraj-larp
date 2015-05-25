package cz.hrajlarp.model.entity;

import javax.persistence.*;

/**
 * Simple representation of part regarding changing password via email.
 */
@Table(name="email_authentication")
@Entity
public class EmailAuthenticatitonEntity {
    private int id;
    private String auth_token;
    private HrajUserEntity user;
    private int user_id;

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_key_gen")
    @SequenceGenerator(name = "id_key_gen", sequenceName = "hraj_email_authentication_id_seq", allocationSize = 1)
    @Column(name = "id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name="auth_token")
    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }


    @Column(name="user_id")
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public HrajUserEntity getUser() {
        return user;
    }

    public void setUser(HrajUserEntity user) {
        this.user = user;
    }
}
