package cz.hrajlarp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    @RequestMapping(value="/zaklady")
    public String basics(){
        return "basics";
    }

    @RequestMapping(value={"/festival","/"})
    public String festival(){
        return "festival";
    }

    @RequestMapping(value="/o-projektu")
    public String about(){
        return "about";
    }

    @RequestMapping(value="/odkazy")
    public String anchors(){
        return "anchors";
    }
}
