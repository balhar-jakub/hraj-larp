package cz.hrajlarp.service;

import cz.hrajlarp.dao.UserAttendedGameDAO;
import cz.hrajlarp.dto.CalendarDto;
import cz.hrajlarp.dto.GameListDto;
import cz.hrajlarp.entity.Game;
import cz.hrajlarp.entity.HrajUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
@Transactional
public class CalendarService {
    @Autowired
    GameService gameService;
    @Autowired
    RightsService rightsService;
    @Autowired
    UserAttendedGameDAO userAttendedGameDAO;
    @Autowired
    DateService dateService;

    public CalendarDto showCalendarAdmin(HrajUser user) {
        List<Game> futureGames = gameService.getFutureGames();
        List<Game> formerGames = gameService.getFormerGames();
        List<Game> invalidGames = gameService.getUnapprovedGames();
        if(!rightsService.isAdministrator(user)){
            invalidGames = new ArrayList<>();
        }

        List<GameListDto> futureGamesResult = new ArrayList<>();
        List<GameListDto> formerGamesResult = new ArrayList<>();
        List<GameListDto> invalidGamesResult = new ArrayList<>();
        // For every game, find if you have right to it.
        for(Game game: futureGames){
            if(rightsService.hasRightsToEditGame(user, game)){
                futureGamesResult.add(transformToListDto(game));
            }
        }
        for(Game game: formerGames){
            if(rightsService.hasRightsToEditGame(user, game)){
                formerGamesResult.add(transformToListDto(game));
            }
        }
        for(Game game: invalidGames){
            if(rightsService.isAdministrator(user)){
                invalidGamesResult.add(transformToListDto(game));
            }
        }

        CalendarDto calendar = new CalendarDto();
        calendar.setAdmin(rightsService.isAdministrator(user));
        calendar.setLogged(true);
        calendar.setFutureGames(futureGamesResult);
        calendar.setPastGames(formerGamesResult);
        calendar.setUnconfirmedGames(invalidGamesResult);
        return calendar;
    }

    public CalendarDto showCalendar() {
        List <Game> futureGames = gameService.getFutureGames();
        List <Game> formerGames = gameService.getFormerGames();

        List<GameListDto> futureGameResult = new ArrayList<>();
        List<GameListDto> formerGamesResult = new ArrayList<>();
        for(Game game: futureGames) {
            if(game.getConfirmed()) {
                futureGameResult.add(transformToListDto(game));
            }
        }
        for(Game game: formerGames) {
            if(game.getConfirmed()) {
                formerGamesResult.add(transformToListDto(game));
            }
        }

        CalendarDto calendar = new CalendarDto();
        calendar.setFutureGames(futureGameResult);
        calendar.setPastGames(formerGamesResult);
        calendar.setLogged(false);

        if (rightsService.isLogged()){
            List<Game> availableGames = userAttendedGameDAO.
                    filterAvailableGames(futureGames, rightsService.getLoggedUser());

            List<GameListDto> availableGamesResult = new ArrayList<>();
            for(Game game: availableGames){
                availableGamesResult.add(transformToListDto(game));
            }

            calendar.setAvailableGames(availableGamesResult);
            calendar.setLogged(true);
        }

        return calendar;
    }

    private GameListDto transformToListDto(Game game) {
        GameListDto result = new GameListDto();

        result.setId(game.getId());
        result.setImage(game.getImagePath());
        result.setName(game.getName());
        result.setInfo(game.getInfo());

        result.setDateAsDayName(dateService.getDateAsDayName(game.getDate()));
        result.setDateAsDM(dateService.getDateAsDM(game.getDate()));
        return result;
    }
}
