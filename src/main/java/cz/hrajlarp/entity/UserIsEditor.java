package cz.hrajlarp.entity;

import javax.persistence.*;

/**
 *
 */
@IdClass(UserGamePK.class)
@Table(name = "user_is_editor", schema = "public")
@Entity
public class UserIsEditor {

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



    private Game editGame;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public Game getEditGame() {
        return editGame;
    }

    public void setEditGame(Game editGame) {
        this.editGame = editGame;
    }


    private HrajUser userEdit;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public HrajUser getUserEdit() {
        return userEdit;
    }

    public void setUserEdit(HrajUser userEdit) {
        this.userEdit = userEdit;
    }
}
