package cz.hrajlarp.controller;

import cz.hrajlarp.dao.GameDAO;
import cz.hrajlarp.dao.PreRegNotificationDAO;
import cz.hrajlarp.dao.UserAttendedGameDAO;
import cz.hrajlarp.dto.GameDto;
import cz.hrajlarp.entity.Game;
import cz.hrajlarp.entity.HrajUser;
import cz.hrajlarp.entity.PreRegNotification;
import cz.hrajlarp.entity.UserAttendedGame;
import cz.hrajlarp.service.DateService;
import cz.hrajlarp.service.GameService;
import cz.hrajlarp.service.RightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.text.SimpleDateFormat;


/**
 *
 */
@Controller
public class GameController {
    @Autowired
    private GameDAO gameDAO;
    @Autowired
    private UserAttendedGameDAO userAttendedGameDAO;
    @Autowired
    private PreRegNotificationDAO preRegNotificationDAO;
    @Autowired
    private RightsService rightsService;
    @Autowired
    private GameService gameService;
    @Autowired
    private DateService dateService;

    /**
     * on submit method for add game form
     * Method fills pseudo bean ValidGame, which is similar to GameEntity, but has only String variables.
     * All variables are validated and error messages are generated if necessary.
     * ValidGame is stored as GameEntity with Hibernate and GameDAO
     *
     * @param myGame    pseudo bean representing form
     * @param result    Returned result
     * @return Path to jsp file
     */
    @RequestMapping(method = RequestMethod.POST, value = "/game/add", produces = "text/plain;charset=UTF-8")
    public String addGame(
            @Valid @ModelAttribute GameDto myGame,
            BindingResult result,
            Model model
    ) {
        if (rightsService.isLogged()) {
            HrajUser user = rightsService.getLoggedUser();
            if (!user.getActivated()) {
                return "/error";
            }

            if (result.hasErrors()) {
                return "game/add";
            }

            boolean confirmedAndAdded = gameService.addGame(myGame);
            model.addAttribute("confirmed", confirmedAndAdded);
            return "/game/added";
        } else {
            return "/error";
        }
    }

    /**
     * Basic view of add game form
     *
     * @param model Model of the page.
     * @return Path to the jsp with information about how to add game.
     */
    @RequestMapping(value = "/game/add", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String addGameShow(ModelMap model) {
        if (rightsService.isLogged()) {
            model.addAttribute("myGame", new GameDto());
            return "game/add";
        } else {
            return "/error";
        }
    }


    /**
     * Method for view of game detail
     *
     * @param id    identifier of the game to view
     * @param model model
     * @return String of .JSP file for game detail view
     */
    @RequestMapping(value = "/game/detail")
    public String detailGame(@RequestParam("gameId") Integer id, Model model) {
        return gameService.getDetailOfGame(id, model);
    }

    /**
     * Basic view method for edit game form
     * Generates date and time from timestamp
     * Loads data from database to prefill form
     *
     * @param id Id of the game, which should be edited.
     */
    @RequestMapping(value = "/game/edit", method = RequestMethod.GET)
    public String editGameForm(Model model,
                               @ModelAttribute("myGame") GameDto myGame,
    						   @RequestParam("id") Integer id) {
        if (id == null || id <= 0) {
            return "/error";
        }

        if (rightsService.isLogged()) {
            Game game = gameService.getGameById(id);
            HrajUser user = rightsService.getLoggedUser();

            if (game != null &&
                    (rightsService.hasRightsToEditGame(user, game))) {
                model.addAttribute("game", game);
                model.addAttribute("date", dateService.getDateAsYMD(game.getDate()));
                model.addAttribute("time", dateService.getTimeAsHM(game.getDate()));
                model.addAttribute("registrationStartedDate",
                        new SimpleDateFormat("yyyy-MM-dd").format(
                                game.getRegistrationStartedDate()));
                model.addAttribute("registrationStartedTime",
                        new SimpleDateFormat("HH:mm").format(
                                game.getRegistrationStartedDate()));

                return "game/edit";
            } else {
                return "admin/norights";
            }
        } else {
            return "error";
        }
    }

    /**
     * On submit editation form for edit game. Pseudo bean ValidGame is filled from form.
     * Method validates all variables and generates errors when necessary.
     * ValidGame is stored as GameEntity and updated with Hibernate and GameDAO
     *
     * @param myGame    pseudo bean representing form variables
     */
    @RequestMapping(value = "/game/edit", method = RequestMethod.POST)
    public String editGame(
            @ModelAttribute("id") Integer id,
            @ModelAttribute("myGame") @Valid GameDto myGame,
            BindingResult result,
            Model model
    ) {

        if (rightsService.isLogged()) {
            if (id == null || id <= 0) {
                return "/error";
            }

            Game gameOld = gameDAO.findById(id);
            HrajUser user = rightsService.getLoggedUser();
            if (!user.getActivated()) {
                return "/error";
            }
            if (gameOld != null && rightsService.hasRightsToEditGame(user, gameOld)) {
                if(result.hasErrors()) {
                    return "/game/edit";
                }
            	gameService.editGameDto(gameOld, myGame, id, model);
                model.addAttribute("gameId", id);
                return "/game/edited";
            }
        }
        return "/error";
    }

    /**
     * Method gets game by given ID and sends its copy into game/add form
     */
    @RequestMapping(value = "/game/copy", method = RequestMethod.POST)
    public String copy(Model model, @RequestParam("gameId") Integer id) {

        if (id == null || id <= 0) {
            return "/error";
        }
        Game game = gameDAO.findById(id);

        if(game == null)
            return "/error";

        Game copiedGame = gameService.cloneGame(game);
        copiedGame.setAddedBy(rightsService.getLoggedUser());

        GameDto copiedGameDto = gameService.transformModelToDto(copiedGame);
        model.addAttribute("myGame", copiedGameDto);
        model.addAttribute("copied", true);
        return "game/add";
    }

    @RequestMapping(value = "/game/regNotifyForm", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String regNotify(@ModelAttribute("gameId") int gameId) {
        if (rightsService.isLogged()) {
            HrajUser user = rightsService.getLoggedUser();
            PreRegNotification regNotify = new PreRegNotification();
            if (gameId > 0) {
	            Game game = gameDAO.findById(gameId);
	            regNotify.setGameId(gameId);
	            regNotify.setUserId(user.getId());
	            regNotify.setNotifyDate(dateService.getDayAgoDate(game.getRegistrationStartedDate()));
	            
	            preRegNotificationDAO.saveOrUpdate(regNotify);
            }
        } else {
            return "/error";
        }
        return "redirect:/game/detail";
    }
}
