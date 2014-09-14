package cz.hrajlarp.controller;

import cz.hrajlarp.model.entity.GameEntity;
import cz.hrajlarp.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
    @Autowired
    GameService gameService;

    @RequestMapping(value = "/zaklady")
    public String basics() {
        return "basics";
    }

    @RequestMapping(value = {"/festival", "/"})
    public String festival(
            Model model
    ) {
        List<GameEntity> nextTwoGames = gameService.getFutureGames(2);
        model.addAttribute("futureGames", !nextTwoGames.isEmpty());
        model.addAttribute("games", nextTwoGames);

        return "festival";
    }

    @RequestMapping(value = "/o-projektu")
    public String about() {
        return "about";
    }

    @RequestMapping(value = "/odkazy")
    public String anchors() {
        return "anchors";
    }
}
