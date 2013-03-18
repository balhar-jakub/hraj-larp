package cz.hrajlarp.controller;

import cz.hrajlarp.model.Game;
import cz.hrajlarp.model.GameDAO;
import cz.hrajlarp.model.GameEntity;
import cz.hrajlarp.model.UserDAO;
import cz.hrajlarp.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Matheo
 * Date: 14.3.13
 * Time: 22:09
 */
@Controller
public class CalendarController {

    @Autowired
    GameDAO gameDAO;

    /**
     * Controller for view of calendar
     * @param model model
     * @return String of .JSP file name mapped for view of calendar
     */
    @RequestMapping(value = "/calendar", method= RequestMethod.GET)
    public String calendar(Model model) {
        System.out.println("CalendarController: Passing through..." + "/calendar");

        List <Game> futureGames = gameDAO.getFutureGames();
        List <Game> formerGames = gameDAO.getFormerGames();

        model.addAttribute("futureGames", futureGames);
        model.addAttribute("formerGames", formerGames);

        /* TODO what should be displayed if some of the lists (or both) is empty */

        return "calendar";
    }

     /* methods used if future and former games were in separated .JSP files */
     /*
    @RequestMapping(value = "/calendar/future")
    public String futureGames(Model model) {
        System.out.println("CalendarController: Passing through..." + "/calendar/future");

        List<Game> games = gameDAO.getFutureGames();
        model.addAttribute("games", games);

        return "calendar/futureEvents";
    }


    @RequestMapping(value = "/calendar/former")
    public String formerGames(Model model) {
        System.out.println("CalendarController: Passing through..." + "/calendar/former");

        List<Game> games = gameDAO.getFormerGames();
        model.addAttribute("games", games);

        return "calendar/formerEvents";
    }
    */
}
