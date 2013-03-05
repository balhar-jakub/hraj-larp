package cz.hrajlarp.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
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
