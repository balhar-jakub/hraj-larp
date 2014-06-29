package cz.hrajlarp.controller.admin;

import cz.hrajlarp.entity.Game;
import cz.hrajlarp.entity.HrajUser;
import cz.hrajlarp.service.GameService;
import cz.hrajlarp.service.PlayerService;
import cz.hrajlarp.service.RightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller relevant for handling players from the administrator point of view.
 */
@Controller
public class PlayersController {
    @Autowired
    private GameService gameService;
    @Autowired
    private RightsService rightsService;
    @Autowired
    private PlayerService playerService;

    /**
     * This method is called, when editor or administrator wants to access players of some game.
     */
    @RequestMapping(value = "/admin/game/players/{id}", method= RequestMethod.GET)
    @Transactional(readOnly = true)
    public String gamePlayers(
            Model model,
            @PathVariable("id") Integer id
    ) {
        HrajUser user = rightsService.getLoggedUser();
        Game game = gameService.getGameById(id);

        if (rightsService.hasRightsToEditGame(user, game)){
            model.addAttribute("gameId",id);
            model.addAttribute("paymentFinished",
                    user.isAccountant() || game.getPaymentFinished());
            model.addAttribute("gameName", game.getName());
            model.addAttribute("players", playerService.getRegularPlayersOfGame(id));
            model.addAttribute("substitutes", playerService.getSubstitutesForGame(id));
            model.addAttribute("isLogged", true);
            return "/admin/game/players";
        } else {
            model.addAttribute("path", "/admin/game/players/" + String.valueOf(id));
            return "/admin/norights";
        }
    }}
