package cz.hrajlarp.controller;

import cz.hrajlarp.entity.Game;
import cz.hrajlarp.service.RightsService;
import cz.hrajlarp.dao.GameDAO;
import cz.hrajlarp.dao.UserAttendedGameDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Controller
public class CalendarController {

    @Autowired
    private GameDAO gameDAO;
    @Autowired
    private UserAttendedGameDAO userAttendedGameDAO;
    @Autowired
    private RightsService rightsService;

    /**
     * Controller for view of calendar
     * @param model model
     * @return String of .JSP file name mapped for view of calendar
     */
    @RequestMapping(value = "/kalendar", method= RequestMethod.GET)
    public String calendar(Model model) {
        List <Game> futureGames = gameDAO.getFutureGames();
        List <Game> formerGames = gameDAO.getFormerGames();

        List<Game> futureGameResult = new ArrayList<>();
        List<Game> formerGamesResult = new ArrayList<>();
        for(Game game: futureGames) {
            if(game.getConfirmed()) {
                futureGameResult.add(game);
            }
        }
        for(Game game: formerGames) {
            if(game.getConfirmed()) {
                formerGamesResult.add(game);
            }
        }

        model.addAttribute("futureGames", futureGameResult);
        model.addAttribute("formerGames", formerGamesResult);

        if (rightsService.isLogged()){
            List<Game> availableGames = userAttendedGameDAO.
                    filterAvailableGames(futureGames, rightsService.getLoggedUser());

            model.addAttribute("availableGames", availableGames);
            model.addAttribute("isLogged", true);
        }
        return "calendar";
    }
}
