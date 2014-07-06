package cz.hrajlarp.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 *
 */
@SuppressWarnings("RedundantIfStatement")
@javax.persistence.IdClass(UserAttendedGamePK.class)
@javax.persistence.Table(name = "user_attended_game", schema = "public")
@Entity
public class UserAttendedGame {

    private boolean substitute;

    @javax.persistence.Column(name = "substitute")
    @Basic
    public boolean isSubstitute() {
        return substitute;
    }

    public void setSubstitute(boolean substitute) {
        this.substitute = substitute;
    }

    private int userId;

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

    private boolean payed;

    @Column(name="payed")
    @Basic
    public boolean getPayed(){
        return payed;
    }

    public void setPayed(boolean payed){
        this.payed = payed;
    }

    private boolean automatic;

    @Column(name="automatic")
    @Basic
    public boolean getAutomatic() {
        return automatic ;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }

    private String variableSymbol;

    @Column(name="variable_symbol")
    @Basic
    public String getVariableSymbol(){
        return variableSymbol;
    }

    public void setVariableSymbol(String variableSymbol){
        this.variableSymbol = variableSymbol;
    }

    private Game attendedGame;

    @ManyToOne
    public
    @javax.persistence.JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    Game getAttendedGame() {
        return attendedGame;
    }

    public void setAttendedGame(Game attendedGame) {
        this.attendedGame = attendedGame;
    }

    private HrajUser userAttended;

    @ManyToOne
    public
    @javax.persistence.JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    HrajUser getUserAttended() {
        return userAttended;
    }

    public void setUserAttended(HrajUser userAttended) {
        this.userAttended = userAttended;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAttendedGame that = (UserAttendedGame) o;

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
}
