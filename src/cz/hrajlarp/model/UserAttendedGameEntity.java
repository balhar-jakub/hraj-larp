package cz.hrajlarp.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Prasek
 * Date: 24.3.13
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.IdClass(cz.hrajlarp.model.UserAttendedGameEntityPK.class)
@javax.persistence.Table(name = "user_attended_game", schema = "public")
@Entity
public class UserAttendedGameEntity {
    private int userId;

    private boolean substitute;

    @Transient
    private String substituteText;

    @javax.persistence.Column(name = "substitute")
    @Basic
    public boolean isSubstitute() {
        return substitute;
    }

    public void setSubstitute(boolean substitute) {
        this.substitute = substitute;
    }

    @javax.persistence.Column(name = "user_id")
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int gameId;

    @javax.persistence.Column(name = "game_id")
    @Id
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    private Timestamp added;

    @javax.persistence.Column(name = "added")
    @Basic
    public Timestamp getAdded() {
        return added;
    }

    public void setAdded(Timestamp added) {
        this.added = added;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAttendedGameEntity that = (UserAttendedGameEntity) o;

        if (gameId != that.gameId) return false;
        if (userId != that.userId) return false;
        if (added != null ? !added.equals(that.added) : that.added != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + gameId;
        result = 31 * result + (added != null ? added.hashCode() : 0);
        return result;
    }

    private GameEntity attendedGame;

    @ManyToOne
    public
    @javax.persistence.JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    GameEntity getAttendedGame() {
        return attendedGame;
    }

    public void setAttendedGame(GameEntity attendedGame) {
        this.attendedGame = attendedGame;
    }

    private HrajUserEntity userAttended;

    @ManyToOne
    public
    @javax.persistence.JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    HrajUserEntity getUserAttended() {
        return userAttended;
    }

    public void setUserAttended(HrajUserEntity userAttended) {
        this.userAttended = userAttended;
    }

    public String getSubstituteText() {
        String result = (isSubstitute()) ? "Náhradník": "Řádný";
        return result;
    }

    public void setSubstituteText(String substituteText) {
        this.substituteText = substituteText;
    }
}
