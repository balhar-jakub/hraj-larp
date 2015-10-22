package cz.hrajlarp.controller;

import cz.hrajlarp.model.Rights;
import cz.hrajlarp.model.ValidGame;
import cz.hrajlarp.model.dao.*;
import cz.hrajlarp.model.entity.*;
import cz.hrajlarp.utils.DateUtils;
import cz.hrajlarp.utils.FileUtils;
import cz.hrajlarp.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;


/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 6.3.13
 * Time: 22:09
 */
@Controller
@Transactional
public class GameController {

    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserAttendedGameDAO userAttendedGameDAO;

    @Autowired
    private UserIsEditorDAO userIsEditorDAO;

    @Autowired
    private Rights rights;

    @Autowired
    private MailService mailService;
    
    @Autowired
    PreRegNotificationDAO preRegNotificationDAO;

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
        if (rights.isLogged()) {
            HrajUserEntity user = rights.getLoggedUser();
            if (!user.getActivated()) return "/error";
            if (imageFile != null && imageFile.length > 0 && !imageFile[0].getOriginalFilename().equals("")) { //there is at least one image file
                String image = saveFile(imageFile, request.getSession().getServletContext(), "gameName");
                myGame.setImage(image);
            } else{
                if(myGame.getImage() == null){
                    myGame.setImage(myGame.getOriginalImage());
                }
            }
            myGame.setAddedBy(rights.getLoggedUser().getId());
            myGame.validate(r);
            myGame.validateDateIsFuture(r);

            if (r.hasErrors()) return "game/add";

            GameEntity game = myGame.getGameEntity();
            if (rights.canAddGameDirectly(user)) {
                game.setConfirmed(true);
                model.addAttribute("confirmed", true);
            } else {
                game.setConfirmed(false);
            }
            gameDAO.addGame(game);
            model.addAttribute("gameId", game.getId());

            UserIsEditorEntity userIsEditorEntity = new UserIsEditorEntity();
            userIsEditorEntity.setGameId(game.getId());
            userIsEditorEntity.setUserId(user.getId());
            userIsEditorDAO.addUserIsEditor(userIsEditorEntity);

            return "/game/added";
        } else return "/error";
    }

    /**
     * Basic view of add game form
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/game/add", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String addGameShow(ModelMap model) {
        if (rights.isLogged()) {
            if(!model.containsKey("myGame") || model.get("myGame") == null)
                model.addAttribute("myGame", new GameEntity());
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

        GameEntity game = gameDAO.getGameById(id);
        if (game != null) {
            try {
                game.countPlayers(userAttendedGameDAO);   //count new free roles count
            } catch (Exception e) {
                e.printStackTrace();
                /* TODO handle error and fix data in the database */
            }

            if (!game.isInFuture()) {
                model.addAttribute("isFuture", true);
            }

            if (rights.isLogged()) {
            	HrajUserEntity user = rights.getLoggedUser();
            	
	            model.addAttribute("regStart", game.getRegistrationStartedDMYHM());
	            
	            if (game.registrationStartsInFuture()) {
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
                boolean logged = userAttendedGameDAO.isLogged(game.getId(), user.getId());
                model.addAttribute("loggedInGame", logged);
                model.addAttribute("isFull", !game.isAvailableToUser(userAttendedGameDAO, user));
                if (logged) {
                    model.addAttribute("substitute", userAttendedGameDAO.isSubstitute(game.getId(), user.getId()));
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
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/game/edit", method = RequestMethod.GET)
    public String editGameForm(Model model,
                               @ModelAttribute("myGame") ValidGame myGame,
    						   @RequestParam("id") Integer id) {
        if (id == null || id <= 0) {
            return "/error";
        }
        //model.addAttribute("myGame", new ValidGame());

        if (rights.isLogged()) {
            GameEntity game = gameDAO.getGameById(id);
            HrajUserEntity user = rights.getLoggedUser();

            if (game != null &&
                    (rights.hasRightsToEditGame(user, game))) {
                myGame.setTextareas(game);
                model.addAttribute("game", game);
                model.addAttribute("date", game.getDateAsYMD());
                model.addAttribute("time", game.getTimeAsHM());
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
     * @param id
     * @param imageFile
     * @param request
     * @param r
     * @param model
     * @return
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

        if (rights.isLogged()) {
            if (id == null || id <= 0) {
                return "/error";
            }

            GameEntity gameOld = gameDAO.getGameById(id);
            HrajUserEntity user = rights.getLoggedUser();
            if (!user.getActivated()) return "/error";
            if (gameOld != null && rights.hasRightsToEditGame(user, gameOld)) {
            	
                if (imageFile != null && imageFile.length > 0 && !imageFile[0].getOriginalFilename().equals("")) { //there is at least one image file
                    String image = saveFile(imageFile, request.getSession().getServletContext(), "gameName");
                    myGame.setImage(image);
                } else {
                    myGame.setImage(gameDAO.getGameById(id).getImage());
                }
                myGame.validate(r);
                if (r.hasErrors()) {
                    return "game/edit";
                }

                GameEntity game = myGame.getGameEntity();
                game.setId(id);
                game.setAddedBy(gameDAO.getGameById(id).getAddedBy());
                if(game.differsInPlayers(gameOld)) {
                    game.rerollLoggedUsers(userAttendedGameDAO, mailService);
                }

                if(rights.isAdministrator(user)){
                    game.setConfirmed(true);
                    model.addAttribute("confirmedNow", true);
                }

                gameDAO.editGame(game);

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
     * @param gameId
     */
    @RequestMapping(value = "/game/logInGame", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String logInGame(
            @ModelAttribute("gameId") int gameId
    ) {
        if (rights.isLogged()) {
            HrajUserEntity user = userDAO.getUserById(rights.getLoggedUser().getId());

            if (gameId > 0) {
            	if (!user.getActivated()) return "/error";
                GameEntity game = gameDAO.getGameById(gameId);
                if (game != null && game.isInFuture()) {
                    // If the user isn't actually logged to the game in the same time frame.
                    Timestamp gameDateFrom = game.getDate();
                    Timestamp gameDateTo = new Timestamp(game.getDate().getTime() + 4 * 60 * 60 * 1000);
                    Collection<UserAttendedGameEntity> attendance = user.getUserEntities().values();
                    for(UserAttendedGameEntity attended: attendance) {
                        Timestamp attendedFrom = attended.getAttendedGame().getDate();
                        Timestamp attendedTo = new Timestamp(attended.getAttendedGame().getDate().getTime() + 4 * 60 * 60 * 1000);
                        if(isAtLeastPartiallyInSameTime(gameDateFrom, gameDateTo, attendedFrom, attendedTo)){
                            return "/errorAlreadyLoggedInTheSameTime";
                        }
                    }

                    try {
                        game.loginAndMailPlayer(userAttendedGameDAO, mailService, user);
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

    private boolean isAtLeastPartiallyInSameTime(Timestamp newlyAttendedFrom, Timestamp newlyAttendedTo, Timestamp game2From, Timestamp game2To) {
        return (game2From.after(newlyAttendedFrom) && game2From.before(newlyAttendedTo)) ||
                (game2To.before(newlyAttendedTo) && game2To.after(newlyAttendedFrom));
    }

    /**
     * This method logs out user from game and sets first substitute user (if exists) as ordinary player.
     * First is checked if user is logged into portal, if game with gameId exists and if record with user and game
     * exists in UserAttendedGame table. Then is user deleted from table. Then is checked free role according to old user gender.
     * New user is choosed from substitutes with the oldest added atribute and right gender.
     *
     * @param gameId
     */
    @RequestMapping(value = "/game/logOutGame", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String logOutGame(
            @ModelAttribute("gameId") int gameId
    ) {

        if (rights.isLogged()) {
            int userId = rights.getLoggedUser().getId();
            
            GameEntity game = gameDAO.getGameById(gameId);
            HrajUserEntity oldUser = userDAO.getUserById(userId);
            if (game != null && game.isInFuture()) {
                if (userAttendedGameDAO.isLogged(game.getId(), oldUser.getId())) {   //user is logged in this game
                    game.logoutAndMailNewRegularPlayer(
                            mailService,
                            userAttendedGameDAO,
                            oldUser
                    );
                }
            }
        } else {
            return "/error";
        }
        return "redirect:/game/detail";
    }

    /**
     * Method gets game by given ID and sends its copy into game/add form
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/game/copy", method = RequestMethod.POST)
    public String copy(Model model, @RequestParam("gameId") Integer id) {

        if (id == null || id <= 0) {
            return "/error";
        }
        GameEntity game = gameDAO.getGameById(id);

        if(game == null)
            return "/error";

        GameEntity copiedGame = game.cloneGame();
        copiedGame.setId(null);
        copiedGame.setAddedBy(rights.getLoggedUser().getId());

        ValidGame validGame = new ValidGame(copiedGame);
        model.addAttribute("myGame", validGame);
        model.addAttribute("copied", true);
        return "game/add";
    }


    /**
     * This method saves image file from form
     *
     * @param imageFile
     * @param context
     * @param gameName
     * @return
     */
    private String saveFile(CommonsMultipartFile[] imageFile, ServletContext context, String gameName) {
        try {
            String path = null;

            if (imageFile != null && imageFile.length > 0 && !imageFile[0].getOriginalFilename().equals("")) {    // there is at least one file attached

                CommonsMultipartFile cmFile = imageFile[0];  // consider only the first attached file

                /* create directories if necessary */
                boolean dirsExists = true;
                File dir = new File(context.getRealPath("assets/img/upload/"));
                if (!(dirsExists = dir.exists()))
                    if (!(dirsExists = dir.mkdirs()))
                        System.out.println("Files could not be created!");

                /* copy attached file into new file on given path */
                if (dirsExists) {
                    String fileType = FileUtils.getFileType(cmFile.getOriginalFilename());
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
        if (rights.isLogged()) {
            HrajUserEntity user = rights.getLoggedUser();
            PreRegNotificationEntity regNotify = new PreRegNotificationEntity();
            if (gameId > 0) {
	            GameEntity game = gameDAO.getGameById(gameId);
	            regNotify.setGameId(gameId);
	            regNotify.setUserId(user.getId());
	            regNotify.setNotifyDate(DateUtils.getDayAgoDate(game.getRegistrationStartedDate()));
	            
	            preRegNotificationDAO.addPreRegNotification(regNotify);
            }
        } else {
            return "/error";
        }
        return "redirect:/game/detail";
    }
}
