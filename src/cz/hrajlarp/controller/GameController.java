package cz.hrajlarp.controller;

import cz.hrajlarp.model.*;
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
public class GameController {

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

                HrajUserEntity user = userDAO.getUserById(game.getAddedBy());

                return "game/detail";
            }catch(Exception e){
                //TODO exception not specified
                model.addAttribute("error", "Hra #" + gameId + " nebyla nalezena");
            }
        }
        return "game/detail";
    }
}
