package cz.hrajlarp.service.admin;

import cz.hrajlarp.dao.UserAttendedGameDAO;
import cz.hrajlarp.dto.PlayerDto;
import cz.hrajlarp.entity.Game;
import cz.hrajlarp.entity.HrajUser;
import cz.hrajlarp.entity.UserAttendedGame;
import cz.hrajlarp.service.GameService;
import cz.hrajlarp.service.PlayerService;
import cz.hrajlarp.service.RightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by jbalhar on 7/2/2014.
 */
@Controller
public class PaymentService {
    @Autowired
    private GameService gameService;
    @Autowired
    private RightsService rightsService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private UserAttendedGameDAO userAttendedGameDAO;

    @RequestMapping(value = "/admin/game/finished/{id}")
    public String paymentFinished(
            Model model,
            @PathVariable("id") Integer id
    ) {
        HrajUser user = rightsService.getLoggedUser();
        Game game = gameService.getGameById(id);

        if (user.isAccountant()) {
            game.setPaymentFinished(true);
            gameService.editGame(game);

            List<PlayerDto> players =
                    playerService.getRegularPlayersOfGame(id);
            List<PlayerDto> substitutes =
                    playerService.getSubstitutesForGame(id);

            model.addAttribute("gameId",id);
            model.addAttribute("paymentFinished", game.getPaymentFinished());
            model.addAttribute("gameName", game.getName());
            model.addAttribute("players", players);
            model.addAttribute("substitutes", substitutes);
            model.addAttribute("isLogged", true);
            return "/admin/game/players";
        } else {
            model.addAttribute("path", "/admin/game/players/" + String.valueOf(id));
            return "/admin/norights";
        }
    }

    @RequestMapping(value="/admin/user/payed/{gameId}/{userId}")
    public String gamePayment(
            Model model,
            @PathVariable("gameId") Integer gameId,
            @PathVariable("userId") Integer userId,
            RedirectAttributes redirectAttributes
    ) {
        HrajUser user = rightsService.getLoggedUser();
        Game game = gameService.getGameById(gameId);
        if (rightsService.hasRightsToEditGame(user, game)){
            UserAttendedGame payingPlayer = userAttendedGameDAO.getLogged(gameId, userId);
            if(payingPlayer != null){
                if(!payingPlayer.getPayed()){
                    payingPlayer.setPayed(true);
                } else {
                    payingPlayer.setPayed(false);
                }
            }
            userAttendedGameDAO.saveOrUpdate(payingPlayer);

            redirectAttributes.addAttribute("id",gameId);
            return "redirect:/admin/game/players/{id}";
        } else {
            model.addAttribute("path", "/admin/user/payed/" +
                    String.valueOf(gameId) + "/" + String.valueOf(userId));
            return "/admin/norights";
        }
    }


}
