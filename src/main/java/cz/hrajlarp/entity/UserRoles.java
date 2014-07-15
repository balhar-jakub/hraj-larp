package cz.hrajlarp.entity;

import cz.hrajlarp.enums.HrajRoles;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by jbalhar on 7/10/2014.
 */
@Entity
@Table(name="roles", schema = "public")
public class UserRoles implements Serializable {
    private HrajUser user;
    private HrajRoles role;

    @Enumerated(EnumType.STRING)
    public HrajRoles getRole() {
        return role;
    }

    public void setRole(HrajRoles role) {
        this.role = role;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public HrajUser getUser() {
        return user;
    }

    public void setUser(HrajUser user) {
        this.user = user;
    }
}
