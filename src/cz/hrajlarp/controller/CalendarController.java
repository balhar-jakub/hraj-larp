package cz.hrajlarp.controller;

import cz.hrajlarp.model.*;
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
 * User: Jakub Balhar
 * Date: 6.3.13
 * Time: 22:09
 */
@Controller
public class CalendarController {

    @Autowired
    GameDAO gameDAO;

    @Autowired
    UserDAO userDAO;

    @RequestMapping(value = "/game/add")
    public String home() {
        System.out.println("HomeController: Passing through...");
        return "game/add";
    }

    @RequestMapping(value = "/game/list")
    public String list(Model model){
        List<Game> games = new ArrayList<Game>();
        games.add(new Game());
        games.add(new Game());
        games.add(new Game());
        model.addAttribute("games", games);
        return "game/list";
    }

    @RequestMapping(value = "/game/detail")
    public String detail(@RequestParam("id") String gameId, Model model) {
        System.out.println("GameController: Passing through..." + "/game/detail" );

        if(gameId != null && !gameId.isEmpty()){
            try{
                int id = Integer.parseInt(gameId);
                //TODO test if ID is valid

                model.addAttribute("gameId", gameId);

                GameEntity game = gameDAO.getGameById(id);
                model.addAttribute("game", game);

                if(game != null && game.getDate() != null){
                    model.addAttribute("dateYMD", DateUtils.getDateAsYMD(game.getDate()));
                    model.addAttribute("dateMD", DateUtils.getDateAsMD(game.getDate()));
                    model.addAttribute("day", DateUtils.getDateAsDayName(game.getDate()));
                    model.addAttribute("time", DateUtils.getTime(game.getDate()));
                }
//                if(game != null && game.getAddedBy()!= null){
//                    HrajUserEntity author = userDAO.getUserById(game.getAddedBy());
//                    model.addAttribute("author", author);
//                }

                return "game/detail";
            }catch(Exception e){
                //TODO exception not specified
                model.addAttribute("error", "Hra #" + gameId + " nebyla nalezena");
            }
        }
        return "game/detail";
    }


    @RequestMapping(value = "/games/future")
    public String futureGames() {
        System.out.println("HomeController: Passing through..." + "/games/future");
        return "game/add";
    }
}
