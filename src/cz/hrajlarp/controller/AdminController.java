package cz.hrajlarp.controller;

import cz.hrajlarp.model.*;
import cz.hrajlarp.utils.MailService;
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
    private GameDAO gameDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserAttendedGameDAO userAttendedGameDAO;

    @Autowired
    private MailService mailService;

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

    @RequestMapping(value = "/admin/game/logout/{gameId}/{playerId}", method= RequestMethod.POST)
    public String logoutPlayer(Model model,
                               @PathVariable("gameId") Integer gameId,
                               @PathVariable("playerId") Integer playerId,
                               RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HrajUserEntity user = userDAO.getUserByLogin(auth.getName());
        if (Rights.isLogged(auth) && user != null){
            GameEntity game = gameDAO.getGameById(gameId);
            HrajUserEntity oldUser = userDAO.getUserById(playerId);
            UserAttendedGameEntity uage = new UserAttendedGameEntity();
            uage.setGameId(gameId);
            uage.setUserId(playerId);
            if (userAttendedGameDAO.isLogged(uage)){   //user is logged in this game
                userAttendedGameDAO.deleteUserAttendedGame(uage);   //logout old user

                List<HrajUserEntity> assignedUsers = userAttendedGameDAO.getUsersByGameId(gameId);
                game.setAssignedUsers(assignedUsers);   //count new free roles count

                int gender = 2;                                   //default setting for none men or women free roles, only both roles are free
                if (oldUser.getGender()==0) {                     //loggouted user is man
                    if (game.getMenFreeRoles() > 0) gender = 0;   //there are free men roles
                }
                else {                                            //loggouted user is woman
                    if (game.getWomenFreeRoles() > 0) gender = 1; //there are free women roles
                }

                uage = userAttendedGameDAO.getFirstSubstitutedUAG(game.getId(), gender);  //get first substitute according to gender
                if (uage != null){
                    HrajUserEntity newUser = userDAO.getUserById(uage.getUserId());
                    uage.setUserId(newUser.getId());
                    uage.setSubstitute(false);
                    userAttendedGameDAO.editUserAttendedGame(uage);             //edit this substitute as ordinary player
                    mailService.sendMessage(newUser, game);
                }
            }

            redirectAttributes.addAttribute("id",gameId);
            return "redirect:/admin/game/players/{id}";
        } else {
            model.addAttribute("path", "/admin/game/logout/" + String.valueOf(gameId) + "/" + playerId);
            return "/admin/norights";
        }
    }
}
