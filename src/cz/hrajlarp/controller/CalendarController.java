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

    @RequestMapping(value = "/calendar")
    public String calendar(Model model) {
        System.out.println("CalendarController: Passing through..." + "/calendar");

        List <Game> futureGames = gameDAO.getFutureGames();
        List <Game> formerGames = gameDAO.getFormerGames();

        model.addAttribute("futureGames", futureGames);
        model.addAttribute("formerGames", formerGames);


        if(futureGames != null && !futureGames.isEmpty())
            for(Game g: futureGames)
                System.out.println("future: " + g.getName() + " " + g.getDateAsYMD());

        if(formerGames != null && !formerGames.isEmpty())
            for(Game g: formerGames)
                System.out.println("former: " + g.getName() + " " + g.getDateAsYMD());

        if(formerGames != null && !formerGames.isEmpty())
            model.addAttribute("testGame", formerGames.get(0));

        return "calendar";
    }

     /* methods used if futere and former games are in separated .JSP files */
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
