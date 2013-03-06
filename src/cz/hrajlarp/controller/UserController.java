package cz.hrajlarp.controller;

import cz.hrajlarp.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 6.3.13
 * Time: 22:14
 */
@Controller
public class UserController {

    @RequestMapping(value="/user/add")
    public String add(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "user/add";
 * Date: 4.3.13
 * Time: 21:52
 */
@Controller
public class UserController {
    @RequestMapping("/uzivatel/pridej")
    public ModelAndView add() {
        User user = new User();
        return new ModelAndView("user/add", "user", user);
    }

    @RequestMapping("/uzivatel/uprav")
    public ModelAndView edit() {
        return new ModelAndView();
    }
}