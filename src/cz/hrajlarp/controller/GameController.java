package cz.hrajlarp.controller;

import cz.hrajlarp.model.Game;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
