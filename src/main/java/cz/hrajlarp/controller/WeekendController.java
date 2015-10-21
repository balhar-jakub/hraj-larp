package cz.hrajlarp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jbalhar on 21. 10. 2015.
 */
@Controller
public class WeekendController {
    @RequestMapping(value = "/vikend", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String festivalInformation() {
        return "weekend/info";
    }


    @RequestMapping(value = "/vikend/hry", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String listOfFestivalGames(
            Model model
    ) {
        return "weekend/games";
    }

    @RequestMapping(value = "/vikend/hraci", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String listOfPlayers(
            Model model
    ) {
        return "weekend/players";
    }
}
