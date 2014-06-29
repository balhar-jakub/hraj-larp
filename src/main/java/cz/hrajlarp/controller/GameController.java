package cz.hrajlarp.controller;

import cz.hrajlarp.dao.GameDAO;
import cz.hrajlarp.dao.PreRegNotificationDAO;
import cz.hrajlarp.dao.UserAttendedGameDAO;
import cz.hrajlarp.dao.UserIsEditorDAO;
import cz.hrajlarp.entity.*;
import cz.hrajlarp.service.FileService;
import cz.hrajlarp.service.GameService;
import cz.hrajlarp.service.RightsService;
import cz.hrajlarp.service.ValidGame;
import cz.hrajlarp.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
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
    private UserIsEditorDAO userIsEditorDAO;
    @Autowired
    private RightsService rightsService;
    @Autowired
    private PreRegNotificationDAO preRegNotificationDAO;
    @Autowired
    private GameService gameService;

    /**
     * on submit method for add game form
     * Method fills pseudo bean ValidGame, which is similar to GameEntity, but has only String variables.
     * All variables are validated and error messages are generated if necessary.
     * ValidGame is stored as GameEntity with Hibernate and GameDAO
     *
     * @param myGame    pseudo bean representing form
     * @param imageFile image uploaded in form
     * @param request   HttpRequest
     * @param r         Returned result
     * @return Path to jsp file
     */
    @RequestMapping(method = RequestMethod.POST, value = "/game/add", produces = "text/plain;charset=UTF-8")
    public String addGame(
            @ModelAttribute("myGame") ValidGame myGame,
            @RequestParam("imageFile") CommonsMultipartFile[] imageFile,
            HttpServletRequest request,
            BindingResult r,
            Model model
    ) {
        if (rightsService.isLogged()) {
            HrajUser user = rightsService.getLoggedUser();
            if (!user.getActivated()) return "/error";
            if (imageFile != null && imageFile.length > 0 && !imageFile[0].getOriginalFilename().equals("")) { //there is at least one image file
                String image = saveFile(imageFile, request.getSession().getServletContext(), "gameName");
                myGame.setImage(image);
            } else{
                if(myGame.getImage() == null){
                    myGame.setImage(myGame.getOriginalImage());
                }
            }
            myGame.setAddedBy(rightsService.getLoggedUser().getId());
            myGame.validate(r);
            myGame.validateDateIsFuture(r);

            if (r.hasErrors()) return "game/add";

            Game game = myGame.getGameEntity();
            if (rightsService.canAddGameDirectly(user)) {
                game.setConfirmed(true);
                model.addAttribute("confirmed", true);
            } else {
                game.setConfirmed(false);
            }
            gameDAO.saveOrUpdate(game);
            model.addAttribute("gameId", game.getId());

            UserIsEditor userIsEditor = new UserIsEditor();
            userIsEditor.setGameId(game.getId());
            userIsEditor.setUserId(user.getId());
            userIsEditorDAO.saveOrUpdate(userIsEditor);

            return "/game/added";
        } else return "/error";
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
            if(!model.containsKey("myGame") || model.get("myGame") == null)
                model.addAttribute("myGame", new Game());
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
        if (id == null || id <= 0) {
            return "kalendar";
        }

        Game game = gameDAO.findById(id);
        if (game != null) {
            if (!gameService.isGameInFuture(game)) {
                model.addAttribute("isFuture", true);
            }

            if (rightsService.isLogged()) {
            	HrajUser user = rightsService.getLoggedUser();
            	
	            model.addAttribute("regStart", gameService.getRegistrationStartedDMYHM(game));
	            
	            if (gameService.registrationStartsInFuture(game)) {
	                model.addAttribute("regStarted", false);
	                if (preRegNotificationDAO.isSubscribedForPreReg(user, game) || DateUtils.isLessThanDayToReg(game.getRegistrationStartedDate()))
	                	model.addAttribute("showNotifRegStart", false);
	                else{
	                	model.addAttribute("showNotifRegStart", true);
	                }
	            } else {
	                model.addAttribute("regStarted", true);
	            }

                model.addAttribute("logged", true);
                boolean logged = userAttendedGameDAO.isSignedAsRegular(game.getId(), user.getId());
                model.addAttribute("loggedInGame", logged);
                model.addAttribute("isFull", !gameService.isAvailableToUser(user, game));
                if (logged) {
                    model.addAttribute("substitute", userAttendedGameDAO.isSignedAsSubstitute(game.getId(), user.getId()));
                }
            }
            model.addAttribute("game", game);
            return "game/detail";

        } else {
            return "/error";
        }
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
                               @ModelAttribute("myGame") ValidGame myGame,
    						   @RequestParam("id") Integer id) {
        if (id == null || id <= 0) {
            return "/error";
        }
        //model.addAttribute("myGame", new ValidGame());

        if (rightsService.isLogged()) {
            Game game = gameDAO.findById(id);
            HrajUser user = rightsService.getLoggedUser();

            if (game != null &&
                    (rightsService.hasRightsToEditGame(user, game))) {
                myGame.setTextareas(game);
                model.addAttribute("game", game);
                model.addAttribute("date", gameService.getDateAsYMD(game));
                model.addAttribute("time", gameService.getTimeAsHM(game));
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
            @ModelAttribute("myGame") ValidGame myGame,
            @RequestParam("imageFile") CommonsMultipartFile[] imageFile,
            HttpServletRequest request,
            BindingResult r,
            Model model
    ) {

        if (rightsService.isLogged()) {
            if (id == null || id <= 0) {
                return "/error";
            }

            Game gameOld = gameDAO.findById(id);
            HrajUser user = rightsService.getLoggedUser();
            if (!user.getActivated()) return "/error";
            if (gameOld != null && rightsService.hasRightsToEditGame(user, gameOld)) {
            	
                if (imageFile != null && imageFile.length > 0 && !imageFile[0].getOriginalFilename().equals("")) { //there is at least one image file
                    String image = saveFile(imageFile, request.getSession().getServletContext(), "gameName");
                    myGame.setImage(image);
                } else {
                    myGame.setImage(gameDAO.findById(id).getImage());
                }
                myGame.validate(r);
                if (r.hasErrors()) {
                    return "game/edit";
                }

                Game game = myGame.getGameEntity();
                game.setId(id);
                game.setAddedBy(gameDAO.findById(id).getAddedBy());
                if(gameService.differsInPlayers(gameOld, game)) {
                    gameService.rerollLoggedUsers(game);
                }

                if(rightsService.isAdministrator(user)){
                    game.setConfirmed(true);
                    model.addAttribute("confirmedNow", true);
                }

                gameDAO.saveOrUpdate(game);

                model.addAttribute("gameId", game.getId());
                return "/game/edited";
            }
        }
        return "/error";
    }

    /**
     * This method logs user to game with gameId. First is checked if user is logged to portal.
     * Then is checked if this user doesnt have record in database for this game. If not, user is
     * added into database and his status is set as substitute if all possible roles are full.
     *
     */
    @RequestMapping(value = "/game/logInGame", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String logInGame(
            @ModelAttribute("gameId") int gameId
    ) {
        if (rightsService.isLogged()) {
            HrajUser user = rightsService.getLoggedUser();

            if (gameId > 0) {
            	if (!user.getActivated()) return "/error";
                Game game = gameDAO.findById(gameId);
                if (game != null && gameService.isGameInFuture(game)) {
                    try {
                        gameService.signUserAsPlayer(game, user);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "/error";
                    }
                }
            }
        } else {
            return "/error";
        }
        return "redirect:/game/detail";
    }

    /**
     * This method logs out user from game and sets first substitute user (if exists) as ordinary player.
     * First is checked if user is logged into portal, if game with gameId exists and if record with user and game
     * exists in UserAttendedGame table. Then is user deleted from table. Then is checked free role according to old user gender.
     * New user is choosed from substitutes with the oldest added atribute and right gender.
     *
     */
    @RequestMapping(value = "/game/logOutGame", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String logOutGame(
            @ModelAttribute("gameId") int gameId
    ) {
        if (rightsService.isLogged()) {
            int userId = rightsService.getLoggedUser().getId();
            UserAttendedGame userAttendedGame = userAttendedGameDAO.getSpecificOne(userId, gameId);

            if(userAttendedGame != null) {
                gameService.signOutFromGame(userAttendedGame);
            }
            return "redirect:/game/detail";
        } else {
            return "/error";
        }
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
        copiedGame.setAddedBy(rightsService.getLoggedUser().getId());

        ValidGame validGame = new ValidGame(copiedGame);
        model.addAttribute("myGame", validGame);
        model.addAttribute("copied", true);
        return "game/add";
    }


    /**
     * This method saves image file from form
     *
     */
    private String saveFile(CommonsMultipartFile[] imageFile, ServletContext context, String gameName) {
        try {
            String path = null;

            if (imageFile != null && imageFile.length > 0 && !imageFile[0].getOriginalFilename().equals("")) {    // there is at least one file attached

                CommonsMultipartFile cmFile = imageFile[0];  // consider only the first attached file

                /* create directories if necessary */
                boolean dirsExists;
                File dir = new File(context.getRealPath("assets/img/upload/"));
                if (!(dirsExists = dir.exists()))
                    if (!(dirsExists = dir.mkdirs()))
                        System.out.println("Files could not be created!");

                /* copy attached file into new file on given path */
                if (dirsExists) {
                    String fileType = FileService.getFileType(cmFile.getOriginalFilename());
                    String basePath = "/" + gameName + "_" + System.currentTimeMillis() + "." + fileType;
                    path = (context.getRealPath("assets/img/upload/") + basePath);
                    cmFile.transferTo(new File(path));
                    path = "/img/upload/" + basePath;
                }
            }
            return path;
        } catch (Exception e) {
            System.out.println("Cant upload file!");
            return null;
        }
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
	            regNotify.setNotifyDate(DateUtils.getDayAgoDate(game.getRegistrationStartedDate()));
	            
	            preRegNotificationDAO.saveOrUpdate(regNotify);
            }
        } else {
            return "/error";
        }
        return "redirect:/game/detail";
    }
}
