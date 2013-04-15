package cz.hrajlarp.controller;

import cz.hrajlarp.model.*;
import cz.hrajlarp.utils.DateUtils;
import cz.hrajlarp.utils.FileUtils;
import cz.hrajlarp.utils.MailService;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 6.3.13
 * Time: 22:09
 */
@Controller
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

    /**
     * on submit method for add game form
     * Method fills pseudo bean ValidGame, which is similar to GameEntity, but has only String variables.
     * All variables are validated and error messages are generated if necessary.
     * ValidGame is stored as GameEntity with Hibernate and GameDAO
     *
     * @param myGame    pseudo bean representing form
     * @param imageFile image uploaded in form
     * @param request HttpRequest
     * @param r Returned result
     * @return Path to jsp file
     */
    @RequestMapping(method = RequestMethod.POST, value = "/game/add", produces = "text/plain;charset=UTF-8")
    public String addGame(
            @ModelAttribute("myGame") ValidGame myGame,
            @RequestParam("imageFile") CommonsMultipartFile[] imageFile,
            HttpServletRequest request,
            BindingResult r
    ) {

        HrajUserEntity user = rights.getLoggedUser();
        if (rights.isLogged()) {
            String image = saveFile(imageFile, request.getSession().getServletContext(), "gameName");
            myGame.setImage(image);
            myGame.setAddedBy(rights.getLoggedUser().getId());
            myGame.validate(r);

            if (r.hasErrors()) return "game/add";

            GameEntity game = myGame.getGameEntity();
            if(rights.isAdministrator(user)) {
                game.setConfirmed(true);
            } else {
                game.setConfirmed(false);
            }
            gameDAO.addGame(game);


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
            model.addAttribute("myGame", new GameEntity());
            return "game/add";
        } else return "/error";
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
        System.out.println("GameController: Passing through..." + "/game/detail" + "gameId: " + id);

        if (id == null || id <= 0) return "kalendar";

        GameEntity game = gameDAO.getGameById(id);
        if (game != null) {
            List<HrajUserEntity> signedUsers = userAttendedGameDAO.getUsersByGameIdNoSubstitutes(game.getId());
            List<HrajUserEntity> substitutes = userAttendedGameDAO.getSubstituteUsersByGameId(game.getId());
            try{
                game.setAssignedUsers(signedUsers, substitutes);   //count new free roles count
            }catch(Exception e){
                e.printStackTrace();
                /* TODO handle error and fix data in the database */
            }
            SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date changedDate = datetimeFormatter1.parse(game.getDate() + " " + game.getTime());
                if (!DateUtils.isFuture(new Timestamp(changedDate.getTime()))) model.addAttribute("isFuture", true);

                Date startRegDate = datetimeFormatter1.parse(game.getRegistrationStarted().toString());
                String regStart = DateUtils.getDateAsDMYHM(startRegDate);
            	model.addAttribute("regStart", regStart);
                if (DateUtils.isFuture(new Timestamp(startRegDate.getTime()))){
                	model.addAttribute("regStarted", false);
                } else {
                    model.addAttribute("regStarted", true);
                }
                
                if (rights.isLogged()) {
                    HrajUserEntity user = rights.getLoggedUser();
                    UserAttendedGameEntity uage = new UserAttendedGameEntity();
                    uage.setGameId(game.getId());
                    uage.setUserId(user.getId());
                    game.setTargetUser(user);
                    model.addAttribute("logged", true);
                    boolean logged = userAttendedGameDAO.isLogged(uage);
                    model.addAttribute("loggedInGame", logged);
                    if (logged) {
                        model.addAttribute("substitute", userAttendedGameDAO.isSubstitute(uage));
                    }
                }
                model.addAttribute("game", game);
                return "game/detail";
            } catch (Exception e) {
                return "/error";
            }
        } else return "/error";
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
    public String editGameForm(Model model, @RequestParam("gameId") Integer id) {

        if (id == null || id <= 0) return "/error";
        model.addAttribute("myGame", new ValidGame());

        if (rights.isLogged()){
            GameEntity game = gameDAO.getGameById(id);
            HrajUserEntity user = rights.getLoggedUser();

            if (game != null && (rights.hasRightsToEditGame(user, game) || gameDAO.userOwnsGame(id, user.getId()))) {
                System.out.println("EDITOR: "+rights.isEditor(user));
                System.out.println("OWNER: "+gameDAO.userOwnsGame(id, user.getId()));
                model.addAttribute("game", game);
                model.addAttribute("date", game.getDate().toString().substring(0, 10));
                model.addAttribute("time", game.getDate().toString().substring(11, 16));
                return "game/edit";
            }
        }
        return "/error";
    }

    /**
     * On submit editation form for edit game. Pseudo bean ValidGame is filled from form.
     * Method validates all variables and generates errors when necessary.
     * ValidGame is stored as GameEntity and updated with Hibernate and GameDAO
     *
     * @param myGame pseudo bean representing form variables
     * @param id
     * @param imageFile
     * @param request
     * @param r
     * @return
     */
    @RequestMapping(value = "/game/edit", method = RequestMethod.POST)
    public String editGame(
            @ModelAttribute("gameId") Integer id,
            @ModelAttribute("myGame") ValidGame myGame,
            @RequestParam("imageFile") CommonsMultipartFile[] imageFile,
            HttpServletRequest request,
            BindingResult r
    ) {

        if (rights.isLogged()) {
            if (id == null || id <= 0) return "/error";

            GameEntity game = gameDAO.getGameById(id);
            HrajUserEntity user = rights.getLoggedUser();

            if (game != null && (rights.hasRightsToEditGame(user, game) || gameDAO.userOwnsGame(game.getId(), user.getId()))) {

                if (imageFile != null && imageFile.length > 0 && !imageFile[0].getOriginalFilename().equals("")) { //there is at least one image file
                    String image = saveFile(imageFile, request.getSession().getServletContext(), "gameName");
                    myGame.setImage(image);
                } else {
                    myGame.setImage(gameDAO.getGameById(id).getImage());
                }
                myGame.validate(r);
                if (r.hasErrors()) return "game/edit";

                game = myGame.getGameEntity();
                game.setId(id);
                game.setAddedBy(gameDAO.getGameById(id).getAddedBy());
                gameDAO.editGame(game);
                System.out.println("Hra byla editovana");
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
            HrajUserEntity user = rights.getLoggedUser();

            if (gameId > 0) {
                GameEntity game = gameDAO.getGameById(gameId);
                if (game != null && DateUtils.isFuture(new Timestamp(DateUtils.stringsToDate(game.getDate().toString(), game.getTime()).getTime()))) {
                    UserAttendedGameEntity uage = new UserAttendedGameEntity();
                    uage.setGameId(gameId);
                    uage.setUserId(user.getId());
                    if (!userAttendedGameDAO.isLogged(uage)) {  //user is not logged in this game
                        List<HrajUserEntity> signedUsers = userAttendedGameDAO.getUsersByGameIdNoSubstitutes(game.getId());
                        List<HrajUserEntity> substitutes = userAttendedGameDAO.getSubstituteUsersByGameId(game.getId());

                        try {
                            game.setAssignedUsers(signedUsers, substitutes);   //count new free roles count
                        } catch (Exception e) {
                            e.printStackTrace();
                            /* TODO handle error and fix data in the database */
                        }
                        
                        game.setTargetUser(user);
                        if (game.isFull()) {
                        	uage.setSubstitute(true);
                        	if (user.getMailInformation())
                        		mailService.sendMsgSignedAsReplacement(user, game);
                        }
                        else {
                        	uage.setSubstitute(false);
                        	if (user.getMailInformation())
                        		mailService.sendMsgSignedAsRegular(user, game);
                        }

                        userAttendedGameDAO.addUserAttendedGame(uage);
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
     * @param gameId
     */
    @RequestMapping(value = "/game/logOutGame", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
    public String logOutGame(
            @ModelAttribute("gameId") int gameId
    ) {

        if (rights.isLogged()) {
            int userId = rights.getLoggedUser().getId();

            if (gameId > 0) {
                GameEntity game = gameDAO.getGameById(gameId);
                HrajUserEntity oldUser = userDAO.getUserById(userId);
                if (game != null&& DateUtils.isFuture(new Timestamp(DateUtils.stringsToDate(game.getDate().toString(), game.getTime()).getTime()))) {
                    UserAttendedGameEntity uage = new UserAttendedGameEntity();
                    uage.setGameId(game.getId());
                    uage.setUserId(oldUser.getId());
                    if (userAttendedGameDAO.isLogged(uage)) {   //user is logged in this game
                        boolean wasSubstitute = userAttendedGameDAO.isSubstitute(uage);
                        userAttendedGameDAO.deleteUserAttendedGame(uage);   //logout old user

                        //count new free roles count
                        List<HrajUserEntity> signedUsers = userAttendedGameDAO.getUsersByGameIdNoSubstitutes(game.getId());
                        List<HrajUserEntity> substitutes = userAttendedGameDAO.getSubstituteUsersByGameId(game.getId());

                        try {
                            game.setAssignedUsers(signedUsers, substitutes);   //count new free roles count
                        } catch (Exception e) {
                            e.printStackTrace();
                            /* TODO handle error and fix data in the database */
                        }

                        int gender = 2;                                   //default setting for none men or women free roles, only both roles are free
                        if (oldUser.getGender() == 0) {                     //loggouted user is man
                            if (game.getMenFreeRoles() > 0) gender = 0;   //there are free men roles
                        } else {                                            //loggouted user is woman
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
                }
            }
        } else {
            return "/error";
        }
        return "redirect:/game/detail";
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
}
