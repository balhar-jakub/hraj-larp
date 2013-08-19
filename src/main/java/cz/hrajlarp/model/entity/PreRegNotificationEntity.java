package cz.hrajlarp.model.entity;

import java.sql.Timestamp;

import javax.persistence.*;

@IdClass(UserGamePK.class)
@Table(name = "prereg_notifications", schema = "public")
@Entity
public class PreRegNotificationEntity {

    private int userId;

    @Column(name = "user_id")
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int gameId;

    @Column(name = "game_id")
    @Id
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
    
    private Timestamp notifyDate;

    @Column(name = "notify_date")
    @Basic
    public Timestamp getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(Timestamp notifyDate) {
        this.notifyDate = notifyDate;
    }

}
