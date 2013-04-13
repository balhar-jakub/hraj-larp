package cz.hrajlarp.model;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Matheo
 * Date: 12.4.13
 * Time: 16:13
 * To change this template use File | Settings | File Templates.
 */
@IdClass(UserGamePK.class)
@Table(name = "user_is_editor", schema = "public")
@Entity
public class UserIsEditorEntity {

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



    private GameEntity editGame;

    @ManyToOne
    @JoinColumn(name = "game_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public GameEntity getEditGame() {
        return editGame;
    }

    public void setEditGame(GameEntity editGame) {
        this.editGame = editGame;
    }


    private HrajUserEntity userEdit;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable=false, updatable=false)
    public HrajUserEntity getUserEdit() {
        return userEdit;
    }

    public void setUserEdit(HrajUserEntity userEdit) {
        this.userEdit = userEdit;
    }
}
