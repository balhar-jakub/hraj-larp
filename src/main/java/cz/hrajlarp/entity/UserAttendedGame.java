package cz.hrajlarp.entity;

import cz.hrajlarp.enums.Status;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 *
 */
@Table(name = "user_attended_game", schema = "public")
@Entity
public class UserAttendedGame {
    private HrajUser user;
    private Game game;

    private boolean payed;
    private boolean automatic;
    private Timestamp added;
    private String variableSymbol;
    private Status status;
    private Timestamp notifyDate;

    @ManyToOne
    @Id
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public HrajUser getUser() {
        return user;
    }

    public void setUser(HrajUser user) {
        this.user = user;
    }

    @ManyToOne
    @Id
    @JoinColumn(name="game_id", referencedColumnName = "id")
    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Column(name = "added")
    @Basic
    public Timestamp getAdded() {
        return added;
    }

    public void setAdded(Timestamp added) {
        this.added = added;
    }

    @Column(name = "payed")
    @Basic
    public boolean getPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    @Column(name = "automatic")
    @Basic
    public boolean getAutomatic() {
        return automatic;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }

    @Column(name = "variable_symbol")
    @Basic
    public String getVariableSymbol() {
        return variableSymbol;
    }

    public void setVariableSymbol(String variableSymbol) {
        this.variableSymbol = variableSymbol;
    }

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Column(name = "notify_date")
    @Basic
    public Timestamp getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(Timestamp notifyDate) {
        this.notifyDate = notifyDate;
    }
}