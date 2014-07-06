package cz.hrajlarp.dto;

import java.util.List;

/**
 * Created by jbalhar on 7/6/2014.
 */
public class CalendarDto {
    private List<GameListDto> futureGames;
    private List<GameListDto> pastGames;
    private List<GameListDto> unconfirmedGames;
    private List<GameListDto> availableGames;
    private boolean isLogged;
    private boolean isAdmin;

    public List<GameListDto> getFutureGames() {
        return futureGames;
    }

    public void setFutureGames(List<GameListDto> futureGames) {
        this.futureGames = futureGames;
    }

    public List<GameListDto> getPastGames() {
        return pastGames;
    }

    public void setPastGames(List<GameListDto> pastGames) {
        this.pastGames = pastGames;
    }

    public List<GameListDto> getUnconfirmedGames() {
        return unconfirmedGames;
    }

    public void setUnconfirmedGames(List<GameListDto> unconfirmedGames) {
        this.unconfirmedGames = unconfirmedGames;
    }

    public List<GameListDto> getAvailableGames() {
        return availableGames;
    }

    public void setAvailableGames(List<GameListDto> availableGames) {
        this.availableGames = availableGames;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
