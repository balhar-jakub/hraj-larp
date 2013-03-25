package cz.hrajlarp.controller;

import cz.hrajlarp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Matheo
 * Date: 14.3.13
 * Time: 22:09
 */
@Controller
public class CalendarController {

    private GameDAO gameDAO;

    @Autowired
    public void setGameDAO(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }


    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    private UserAttendedGameDAO userAttendedGameDAO;

    @Autowired
    public void setUserAttendedGameDAO(UserAttendedGameDAO userAttendedGameDAO) {
        this.userAttendedGameDAO = userAttendedGameDAO;
    }

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

        HrajUserEntity testUser = userDAO.getUserById(2);

        List<Game> availableGames = userAttendedGameDAO.filterAvailableGames(futureGames, testUser); /* TODO gender must be set by logged user from cookie or session */
        model.addAttribute("availableGames", availableGames);

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
