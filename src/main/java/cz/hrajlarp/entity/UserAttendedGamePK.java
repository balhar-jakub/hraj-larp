package cz.hrajlarp.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 *
 */
public class UserAttendedGamePK implements Serializable {

    public UserAttendedGamePK(){ }

    public UserAttendedGamePK(int userId, int gameId) {
        this.userId = userId;
        this.gameId = gameId;
    }

    private int userId;

    @Id
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int gameId;

    @Id
    @Column(name = "game_id")
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAttendedGamePK)) return false;

        UserAttendedGamePK that = (UserAttendedGamePK) o;

        if (gameId != that.gameId) return false;
        if (userId != that.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + gameId;
        return result;
    }
}
