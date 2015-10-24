package cz.hrajlarp.controller;

import cz.hrajlarp.model.dao.GameDAO;
import cz.hrajlarp.model.dao.UserAttendedGameDAO;
import cz.hrajlarp.model.entity.GameEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jbalhar on 21. 10. 2015.
 */
@Controller
public class WeekendController {
    @Autowired
    private GameDAO games;
    @Autowired
    private UserAttendedGameDAO players;

    @RequestMapping(value = "/vikend", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String festivalInformation() {
        return "weekend/info";
    }


    @RequestMapping(value = "/vikend/hry", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String listOfFestivalGames(
            Model model
    ) {
        List<GameEntity> weekendGames = games.getGamesWithAction("HRAJ LARP víkend");

        List<GameEntity> fridayGames = new ArrayList<>();
        List<GameEntity> saturdayMorningGames = new ArrayList<>();
        List<GameEntity> saturdayAfternoonGames = new ArrayList<>();
        List<GameEntity> sundayGames = new ArrayList<>();
        List<GameEntity> sleepOver = new ArrayList<>();

        for(GameEntity game: weekendGames){
            try {
                game.countPlayers(players);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(game.getName().equals("Přespání Pátek/Sobota")) {
                sleepOver.add(game);
                continue;
            } else if(game.getName().equals("Přespání Sobota/Neděle")) {
                sleepOver.add(game);
                continue;
            }

            if(game.getDateAsDM().equals("04.12")) {
                fridayGames.add(game);
            } else if(game.getDateAsDM().equals("06.12")) {
                sundayGames.add(game);
            } else if(game.getDateAsDM().equals("05.12")) {
                if(game.getTimeAsHM().equals("09:00")) {
                    saturdayMorningGames.add(game);
                } else {
                    saturdayAfternoonGames.add(game);
                }
            }
        }

        model.addAttribute("games1", fridayGames);
        model.addAttribute("games2", saturdayMorningGames);
        model.addAttribute("games3", saturdayAfternoonGames);
        model.addAttribute("games4", sundayGames);
        model.addAttribute("sleepOver", sleepOver);

        return "weekend/games";
    }

    @RequestMapping(value = "/vikend/hraci", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String listOfPlayers(
            Model model
    ) {
        return "weekend/players";
    }
}
