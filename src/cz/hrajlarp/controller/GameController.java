package cz.hrajlarp.controller;

import cz.hrajlarp.model.Game;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
 * Date: 21.2.13
 * Time: 15:56
 */
@Controller
public class GameController {
    @RequestMapping("/hra/pridej")
    public ModelAndView add() {
        return new ModelAndView();
    }

    @RequestMapping("/hra/smaz")
    public ModelAndView remove() {
        return new ModelAndView();
    }

    @RequestMapping("/hra/uprav")
    public ModelAndView edit() {
        return new ModelAndView();
    }

    @RequestMapping("/hra/vypis")
    public ModelAndView list() {
        return new ModelAndView();
    }

    @RequestMapping("/hra/detail")
    public ModelAndView detail() {
        return new ModelAndView();
    }
}
