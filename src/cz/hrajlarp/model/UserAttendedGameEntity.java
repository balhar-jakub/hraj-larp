package cz.hrajlarp.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@javax.persistence.Table(name = "user_attended_game", schema = "public", catalog = "")
@Entity
@AssociationOverrides({
        @AssociationOverride(name="pk.hrajUser",
                joinColumns = @JoinColumn(name="user_id")),
        @AssociationOverride(name="pk.game",
                joinColumns = @JoinColumn(name="game_id"))
})
public class UserAttendedGameEntity implements Serializable {
    private UserAttendedGameId pk = new UserAttendedGameId();
    private Date added;

    public UserAttendedGameEntity(){
    }

    @EmbeddedId
    public UserAttendedGameId getPk() {
        return pk;
    }

    public void setPk(UserAttendedGameId pk) {
        this.pk = pk;
    }

    @Transient
    public GameEntity getGameEntity(){
        return getPk().getGame();
    }

    public void setGameEntity(GameEntity gameEntity){
        getPk().setGame(gameEntity);
    }

    @Transient
    public HrajUserEntity getHrajUserEntity(){
        return getPk().getHrajUser();
    }

    public void setHrajUserEntity(HrajUserEntity hrajUser){
        getPk().setHrajUser(hrajUser);
    }

    @Temporal(TemporalType.DATE)
    @Column(name="added", nullable = false, length = 10)
    public Date getAdded(){
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserAttendedGameEntity that = (UserAttendedGameEntity) o;

        if (getPk() != null ? !getPk().equals(that.getPk())
                : that.getPk() != null)
            return false;

        return true;
    }

    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }
}
