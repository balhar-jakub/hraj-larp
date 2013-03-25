package cz.hrajlarp.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Matheo
 * Date: 22.3.13
 * Time: 11:22
 * To change this template use File | Settings | File Templates.
 */
@Table(name="user_attended_game", schema="public", catalog="")
@Entity
public class GameUserEntity {

    private Integer gameId;

    @javax.persistence.Column(name = "game_id")
    @Id
    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    private Integer userId;

    @javax.persistence.Column(name = "user_id")
    @Id
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    private Timestamp added;

    @javax.persistence.Column(name = "added")
    @Basic
    public Timestamp getAdded() {
        return added;
    }

    public void setAdded(Timestamp added){
         this.added = added;
    }
}
