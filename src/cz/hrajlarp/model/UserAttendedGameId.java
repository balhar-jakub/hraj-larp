package cz.hrajlarp.model;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class UserAttendedGameId implements Serializable {
    private GameEntity game;
    private HrajUserEntity hrajUser;

    @ManyToOne
    @JoinColumn(name="game_id")
    public GameEntity getGame(){
        return game;
    }

    public void setGame(GameEntity game){
        this.game = game;
    }

    @ManyToOne
    @JoinColumn(name="user_id")
    public HrajUserEntity getHrajUser(){
        return hrajUser;
    }

    public void setHrajUser(HrajUserEntity hrajUser){
        this.hrajUser = hrajUser;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAttendedGameId that = (UserAttendedGameId) o;

        if (game != null ? !game.equals(that.game) : that.game != null) return false;
        if (hrajUser != null ? !hrajUser.equals(that.hrajUser) : that.hrajUser != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (game != null ? game.hashCode() : 0);
        result = 31 * result + (hrajUser != null ? hrajUser.hashCode() : 0);
        return result;
    }
}
