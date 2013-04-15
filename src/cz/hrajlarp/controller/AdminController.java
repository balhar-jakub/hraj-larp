package cz.hrajlarp.controller;

import cz.hrajlarp.model.*;
import cz.hrajlarp.utils.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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

    @Autowired
    private Rights rights;

    @RequestMapping(value = "/admin/game/players/{id}", method= RequestMethod.GET)
    public String gamePlayers(Model model, @PathVariable("id") Integer id) {
        HrajUserEntity user = rights.getLoggedUser();
        GameEntity game = gameDAO.getGameById(id);

        if (rights.hasRightsToEditGame(user, game)){
            List<HrajUserEntity> players =  userAttendedGameDAO.getUsersByGameIdNoSubstitutes(id);
            List<HrajUserEntity> substitutes =  userAttendedGameDAO.getSubstituteUsersByGameId(id);

            model.addAttribute("gameId",id);
            model.addAttribute("players", players);
            model.addAttribute("substitutes", substitutes);
            model.addAttribute("isLogged", true);
            return "/admin/game/players";
        } else {
            model.addAttribute("path", "/admin/game/players/" + String.valueOf(id));
            return "/admin/norights";
        }
    }

    @RequestMapping(value = "/admin/game/list", method= RequestMethod.GET)
    public String gameList(Model model) {
        HrajUserEntity user = rights.getLoggedUser();

        if (rights.isEditor(user) || rights.isAdministrator(user)){
            List <GameEntity> futureGames = gameDAO.getFutureGames();
            List <GameEntity> formerGames = gameDAO.getFormerGames();
            List <GameEntity> invalidGames = gameDAO.getInvalidGames();
            if(!rights.isAdministrator(user)){
                invalidGames = new ArrayList<GameEntity>();
            }

            List<GameEntity> futureGamesResult = new ArrayList<GameEntity>();
            List<GameEntity> formerGamesResult = new ArrayList<GameEntity>();
            // For every game, find if you have right to it.
            for(GameEntity game: futureGames){
                if(rights.hasRightsToEditGame(user, game)){
                    futureGamesResult.add(game);
                }
            }
            for(GameEntity game: formerGames){
                if(rights.hasRightsToEditGame(user, game)){
                    formerGamesResult.add(game);
                }
            }

            model.addAttribute("futureGames", futureGamesResult);
            model.addAttribute("formerGames", formerGamesResult);
            model.addAttribute("unvalidatedGames", invalidGames);
            model.addAttribute("isLogged", true);
            return "/admin/game/list";
        } else {
            model.addAttribute("path", "/admin/game/list");
            return "/admin/norights";
        }
    }

    @RequestMapping(value = "/admin/game/logout/{gameId}/{playerId}", method= RequestMethod.POST)
    public String logoutPlayer(Model model,
                               @PathVariable("gameId") Integer gameId,
                               @PathVariable("playerId") Integer playerId,
                               RedirectAttributes redirectAttributes) {
        HrajUserEntity user = rights.getLoggedUser();
        GameEntity game = gameDAO.getGameById(gameId);

        if (rights.hasRightsToEditGame(user, game)){
            HrajUserEntity oldUser = userDAO.getUserById(playerId);
            UserAttendedGameEntity uage = new UserAttendedGameEntity();
            uage.setGameId(gameId);
            uage.setUserId(playerId);
            if (userAttendedGameDAO.isLogged(uage)){   //user is logged in this game
                boolean wasSubstitute = userAttendedGameDAO.isSubstitute(uage);
                userAttendedGameDAO.deleteUserAttendedGame(uage);   //logout old user

                List<HrajUserEntity> signedUsers = userAttendedGameDAO.getUsersByGameIdNoSubstitutes(gameId);
                List<HrajUserEntity> substitutes = userAttendedGameDAO.getSubstituteUsersByGameId(gameId);

                try{
                    game.setAssignedUsers(signedUsers, substitutes);   //count new free roles count
                }catch(Exception e){
                    e.printStackTrace();
                    /* TODO handle error and fix data in the database */
                }

                int gender = 2;                                   //default setting for none men or women free roles, only both roles are free
                if (oldUser.getGender()==0) {                     //loggouted user is man
                    if (game.getMenFreeRoles() > 0) gender = 0;   //there are free men roles
                }
                else {                                            //loggouted user is woman
                    if (game.getWomenFreeRoles() > 0) gender = 1; //there are free women roles
                }

                /* if logged out user was not just a substitute, some of substitutes should replace him */
                if(!wasSubstitute){
                    uage = userAttendedGameDAO.getFirstSubstitutedUAG(game.getId(), gender);  //get first substitute according to gender
                    if (uage != null) {
                        HrajUserEntity newUser = userDAO.getUserById(uage.getUserId());
                        uage.setUserId(newUser.getId());
                        uage.setSubstitute(false);
                        userAttendedGameDAO.editUserAttendedGame(uage);             //edit this substitute as ordinary player
                        mailService.sendMsgChangedToActor(newUser, game);
                    }
                }
            }

            redirectAttributes.addAttribute("id",gameId);
            return "redirect:/admin/game/players/{id}";
        } else {
            model.addAttribute("path", "/admin/game/logout/" + String.valueOf(gameId) + "/" + playerId);
            return "/admin/norights";
        }
    }

    @RequestMapping(value = "/admin/game/validate/{gameId}")
    public String validateGame(Model model,
                               @PathVariable("gameId") Integer gameId,
                               RedirectAttributes redirectAttributes) {
        HrajUserEntity user = rights.getLoggedUser();
        if (rights.isAdministrator(user)){
            GameEntity game = gameDAO.getGameById(gameId);
            game.setConfirmed(true);
            gameDAO.editGame(game);

            return "redirect:/admin/game/list";
        } else {
            model.addAttribute("path", "/admin/game/validate/" + String.valueOf(gameId));
            return "/admin/norights";
        }
    }
    
    @RequestMapping(value = "/admin/game/mail/{gameId}")
    public String editMailText(Model model, @PathVariable("gameId") Integer gameId,
                               RedirectAttributes redirectAttributes) {
        GameEntity game = gameDAO.getGameById(gameId);
        HrajUserEntity user = rights.getLoggedUser();

        if (rights.hasRightsToEditGame(user, game)){
        	model.addAttribute("editMailForm", new GameEntity());
            model.addAttribute("game", game);
            
            return "admin/game/editmail";
        } else {
            model.addAttribute("path", "/admin/game/mail/" + String.valueOf(gameId));
            return "/admin/norights";
        }
    }
    
    @RequestMapping(value="/admin/game/editmail", method = RequestMethod.POST)
    public String submitMail(Model model, @ModelAttribute("editMailForm") GameEntity game) {
        HrajUserEntity user = rights.getLoggedUser();
        if (rights.hasRightsToEditGame(user, game)){
            Integer Id = game.getId();
        	String opt = game.getOrdinaryPlayerText();
        	String rt = game.getReplacementsText();
            GameEntity g = gameDAO.getGameById(Id);
            g.setOrdinaryPlayerText(opt);
            g.setReplacementsText(rt);
            gameDAO.editGame(g);
        	
            return "admin/game/list";
        } else {
            model.addAttribute("path", "/admin/game/editmail/" + String.valueOf(game.getId()));
            return "/admin/norights";
        }
    }
}
