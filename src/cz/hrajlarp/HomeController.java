package cz.hrajlarp.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    @RequestMapping("/hello")
    public ModelAndView helloWorld() {
        String message = "Hello World, Spring 3.0!";
        return new ModelAndView("hello", "message", message);
    }
}
