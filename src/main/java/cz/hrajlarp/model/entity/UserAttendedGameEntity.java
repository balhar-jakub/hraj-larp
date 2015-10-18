package cz.hrajlarp.model.entity;

import cz.hrajlarp.service.MailService;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Prasek
 * Date: 24.3.13
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.IdClass(UserGamePK.class)
@javax.persistence.Table(name = "user_attended_game", schema = "public")
@Entity
public class UserAttendedGameEntity {

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

    private Boolean payed;

    @Column(name="payed")
    @Basic
    public Boolean getPayed(){
        return payed;
    }

    public void setPayed(Boolean payed){
        this.payed = payed;
    }

    private Boolean automatic;

    @Column(name="automatic")
    @Basic
    public Boolean getAutomatic() {
        if(automatic == null) {
            automatic = false;
        }
        return automatic ;
    }

    public void setAutomatic(Boolean automatic) {
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
    @javax.persistence.JoinColumn(name = "game_id", referencedColumnName = "`id`", nullable = false, insertable=false, updatable=false)
    GameEntity getAttendedGame() {
        return attendedGame;
    }

    public void setAttendedGame(GameEntity attendedGame) {
        this.attendedGame = attendedGame;
    }

    private HrajUserEntity userAttended;

    @ManyToOne
    public
    @javax.persistence.JoinColumn(name = "user_id", referencedColumnName = "`id`", nullable = false, insertable=false, updatable=false)
    HrajUserEntity getUserAttended() {
        return userAttended;
    }

    public void setUserAttended(HrajUserEntity userAttended) {
        this.userAttended = userAttended;
    }

    @Transient
    public String getSubstituteText() {
        return (isSubstitute()) ? "Náhradník": "Hráč";
    }

    public void setSubstituteText(String substituteText) {
        this.substituteText = substituteText;
    }

    @Transient
    public void notifyByMail(MailService mailService) {
        //To change body of created methods use File | Settings | File Templates.
    	if(getAttendedGame().getMailProhibition()) return;
        if(!isSubstitute()){
            mailService.sendMsgSignedAsRegular(getUserAttended(), getAttendedGame());
        } else {
            mailService.sendMsgSignedAsReplacement(getUserAttended(), getAttendedGame());
        }
    }

    @Transient
    public void notifyChangedByMail(MailService mailService) {
        if(!isSubstitute()) {
        	mailService.sendMsgChangedToActor(getUserAttended(), getAttendedGame());
            mailService.sendMsgToEditors(getUserAttended(), getAttendedGame());
        } else {
            mailService.sendMsgChangedToReplacement(getUserAttended(), getAttendedGame());
        }
    }

    private String payedTextual;

    @Transient
    public String getPayedTextual(){
        return (payed != null && payed) ? "Ano" : "";
    }

    @Transient
    public void setPayedTextual(String payedTextual){
        this.payedTextual = payedTextual;
    }
}
