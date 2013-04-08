package cz.hrajlarp.controller;

import cz.hrajlarp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 8.4.13
 * Time: 15:54
 */
@Controller
public class AdminController {
    @Autowired
    GameDAO gameDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    UserAttendedGameDAO userAttendedGameDAO;

    @RequestMapping(value = "/admin/game/players/{id}", method= RequestMethod.GET)
    public String gamePlayers(Model model, @PathVariable("id") Integer id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HrajUserEntity user = userDAO.getUserByLogin(auth.getName());
        if (Rights.isLogged(auth) && user != null){
            List<HrajUserEntity> players =  userAttendedGameDAO.getUsersByGameId(id);

            model.addAttribute("gameId",id);
            model.addAttribute("players", players);
            model.addAttribute("isLogged", true);
            return "/admin/game/players";
        } else {
            model.addAttribute("path", "/admin/game/players/" + String.valueOf(id));
            return "/admin/norights";
        }
    }

    @RequestMapping(value = "/admin/game/logout/{gameId}/{playerId}", method= RequestMethod.GET)
    public String logoutPlayer(Model model,
                               @PathVariable("gameId") Integer gameId,
                               @PathVariable("playerId") Integer playerId,
                               RedirectAttributes redirectAttributes) {

        // TODO logout the user.

        redirectAttributes.addFlashAttribute("gameId",gameId);
        return "redirect:/admin/game/players";
    }
}
