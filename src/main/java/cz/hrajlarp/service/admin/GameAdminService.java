package cz.hrajlarp.service.admin;

import cz.hrajlarp.dto.GameListDto;
import cz.hrajlarp.entity.Game;
import cz.hrajlarp.entity.HrajUser;
import cz.hrajlarp.service.GameService;
import cz.hrajlarp.service.RightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
@Transactional
public class GameAdminService {
    @Autowired
    GameService gameService;
    @Autowired
    RightsService rightsService;

    public void showCalendar(HrajUser user, Model model) {
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

        model.addAttribute("futureGames", futureGamesResult);
        model.addAttribute("formerGames", formerGamesResult);
        model.addAttribute("unvalidatedGames", invalidGamesResult);
        model.addAttribute("isLogged", true);
        model.addAttribute("isAdmin", rightsService.isAdministrator(user));
    }

    private GameListDto transformToListDto(Game game) {
        GameListDto result = new GameListDto();

        result.setId(game.getId());
        result.setImage(game.getImage());
        result.setName(game.getName());
        result.setInfo(game.getInfo());

        result.setDateAsDayName(gameService.getDateAsDayName(game));
        result.setDateAsDM(gameService.getDateAsDM(game));
        return result;
    }
}
