package cz.hrajlarp.controller;

import cz.hrajlarp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
import java.security.Security;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 6.3.13
 * Time: 22:09
 */
@Controller
public class GameController{

    @Autowired
    private GameDAO gameDAO;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserAttendedGameDAO userAttendedGameDAO;
    @Autowired
    private Rights rights;

    /**
     * on submit method for add game form
     * Method fills pseudo bean ValidGame, which is similar to GameEntity, but has only String variables.
     * All variables are validated and error messages are generated if necessary.
     * ValidGame is stored as GameEntity with Hibernate and GameDAO
     * @param myGame pseudo bean representing form
     * @param imageFile image uploaded in form
     * @param request
     * @param r
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/game/add", produces="text/plain;charset=UTF-8")
    public String onSubmit(
            @ModelAttribute("myGame") ValidGame myGame,
            @RequestParam("imageFile") CommonsMultipartFile[] imageFile,
            HttpServletRequest request,
            BindingResult r
            ){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HrajUserEntity user = userDAO.getUserByLogin(auth.getName());

        if (rights.isLogged(auth) && user!= null){

            String image = saveFile(imageFile, request.getSession().getServletContext(), "gameName");
            myGame.setImage(image);
            myGame.setAddedBy(user.getId());
            myGame.validate(r);

            if (r.hasErrors()) return "game/add";

            GameEntity game = myGame.getGameEntity();
            gameDAO.addGame(game);

            System.out.println("Formular odeslan");
            return "/game/added";
        }
        else return "/error";
    }

    /**
     * Basic view of add game form
     * @param model
     * @return
     */
    @RequestMapping(value = "/game/add", method= RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public String showForm(ModelMap model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HrajUserEntity user = userDAO.getUserByLogin(auth.getName());

        if (rights.isLogged(auth) && user!=null){
            model.addAttribute("myGame", new GameEntity());
            return "game/add";
        }
        else return "/error";
    }


    /**
     * Method for view of game detail
     * @param gameId id of the game to view
     * @param model model
     * @return  String of .JSP file for game detail view
     */
    @RequestMapping(value="/game/detail")
    public String detail(@RequestParam("gameId") String gameId, Model model) {
        System.out.println("GameController: Passing through..." + "/game/detail" + "gameId: " + gameId );
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(gameId == null || gameId.isEmpty()) return "calendar";

        try {
            int id = Integer.parseInt(gameId);
            if (id <= 0)
            throw new Exception();

            GameEntity game = gameDAO.getGameById(id);

            List<HrajUserEntity> assignedUsers = userAttendedGameDAO.getUsersByGameId(game.getId());
            game.setAssignedUsers(assignedUsers);
            model.addAttribute("game", game);

            HrajUserEntity user = userDAO.getUserByLogin(auth.getName());
            if (rights.isLogged(auth) && user != null){
                UserAttendedGameEntity uage = new UserAttendedGameEntity();
                uage.setGameId(game.getId());
                uage.setUserId(user.getId());
                game.setTargetUser(user);
                model.addAttribute("logged", true);
                boolean logged = userAttendedGameDAO.isLogged(uage);
                model.addAttribute("loggedInGame", logged);
                if (logged) model.addAttribute("substitute", userAttendedGameDAO.isSubstitute(uage));
            }

        } catch (Exception e) {
            /* TODO error message is too brief and not styled in .JSP file*/
            model.addAttribute("error", "Hra #" + gameId + " nebyla nalezena");
        }

        return "game/detail";
    }

    /**
     * Basic view method for edit game form
     * Generates date and time from timestamp
     * Loads data from database to prefill form
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/game/edit", method= RequestMethod.GET)
    public String showEditForm(Model model, @ModelAttribute("id") String id){
        model.addAttribute("myGame", new ValidGame());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HrajUserEntity user = userDAO.getUserByLogin(auth.getName());
        if (rights.isLogged(auth) && user != null){
            int gameId = Integer.parseInt(id);
            if (gameId < 0) return "/error";

            if (gameDAO.userOwnsGame(gameId, user.getId())){
                try {
                    GameEntity game = gameDAO.getGameById(gameId);

                    if (game != null) {
                        model.addAttribute("game", game);
                        model.addAttribute("date", game.getDate().toString().substring(0, 10));
                        model.addAttribute("time", game.getDate().toString().substring(11,16));
                        return "game/edit";
                    }
                    else {
                        return "/error";
                    }
                    }
                    catch (NumberFormatException e) {
                        return "/error";
                    }
            }
        }
        return "/error";
    }

    /**
     * On submit editation form for edit game. Pseudo bean ValidGame is filled from form.
     * Method validates all variables and generates errors when necessary.
     * ValidGame is stored as GameEntity and updated with Hibernate and GameDAO
     * @param myGame pseudo bean representing form variables
     * @param r
     * @return
     */
    @RequestMapping(value = "/game/edit", method = RequestMethod.POST)
    public String onSubmitEditForm(
            @ModelAttribute("gameId") String id,
            @ModelAttribute("myGame") ValidGame myGame,
            @RequestParam("imageFile") CommonsMultipartFile[] imageFile,
            HttpServletRequest request,
            BindingResult r
            ){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HrajUserEntity user = userDAO.getUserByLogin(auth.getName());

        if (rights.isLogged(auth) && user != null){
            int gameId = Integer.parseInt(id);
            if (gameId < 0) return "/error";

            if (gameDAO.userOwnsGame(gameId, user.getId())){
                try {

                    if (imageFile != null && imageFile.length > 0 && !imageFile[0].getOriginalFilename().equals("")) { //there is at least one image file
                        String image = saveFile(imageFile, request.getSession().getServletContext(), "gameName");
                        myGame.setImage(image);
                    }
                    else {
                        myGame.setImage(gameDAO.getGameById(gameId).getImage());
                    }
                    myGame.validate(r);
                    if (r.hasErrors()) return "game/edit";

                    GameEntity game = myGame.getGameEntity();
                    game.setId(gameId);
                    gameDAO.editGame(game);
                    System.out.println("Hra byla editovana");
                    return "/game/edited";
                }
                catch (NumberFormatException e){
                    return "/error";
                }
            }
        }
        return "/error";
    }

    /**
     * This method logs user to game with gameId. First is checked if user is logged to portal.
     * Then is checked if this user doesnt have record in database for this game. If not, user is
     * added into database and his status is set as substitute if all possible roles are full.
     * @param gameId
     */
    @RequestMapping(value = "/game/logInGame", method= RequestMethod.POST, produces="text/plain;charset=UTF-8")
    public String logInGame(
            @ModelAttribute("gameId") int gameId
    ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HrajUserEntity user = userDAO.getUserByLogin(auth.getName());

        if (rights.isLogged(auth) && user!=null){
            int userId = user.getId();

            if (gameId > 0){
                GameEntity game = gameDAO.getGameById(gameId);
                if (game != null){
                    UserAttendedGameEntity uage = new UserAttendedGameEntity();
                    uage.setGameId(gameId);
                    uage.setUserId(userId);
                    if (!userAttendedGameDAO.isLogged(uage)){  //user is not logged in this game
                        List<HrajUserEntity> assignedUsers = userAttendedGameDAO.getUsersByGameId(game.getId());
                        game.setAssignedUsers(assignedUsers);
                        game.setTargetUser(user);
                        if (game.isFull()) uage.setSubstitute(true);
                        else uage.setSubstitute(false);
                        userAttendedGameDAO.addUserAttendedGame(uage);
                    }
                }
            }
        }
        else {
            //TODO error page: you have to log in first
        }
        return "redirect:/game/detail";
    }

    /**
     * This method logs out user from game and sets first substitute user (if exists) as ordinary player.
     * First is checked if user is logged into portal, if game with gameId exists and if record with user and game
     * exists in UserAttendedGame table. Then is user deleted from table. Then is checked free role according to old user gender.
     * New user is choosed from substitutes with the oldest added atribute and right gender.
     * @param gameId
     */
    @RequestMapping(value = "/game/logOutGame", method= RequestMethod.POST, produces="text/plain;charset=UTF-8")
    public String logOutGame(
            @ModelAttribute("gameId") int gameId
    ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HrajUserEntity user = userDAO.getUserByLogin(auth.getName());

        if (rights.isLogged(auth) && user != null){
            int userId = user.getId();

            if (gameId > 0){
                GameEntity game = gameDAO.getGameById(gameId);
                HrajUserEntity oldUser = userDAO.getUserById(userId);
                if (game != null){
                    UserAttendedGameEntity uage = new UserAttendedGameEntity();
                    uage.setGameId(game.getId());
                    uage.setUserId(oldUser.getId());
                    if (userAttendedGameDAO.isLogged(uage)){   //user is logged in this game
                        userAttendedGameDAO.deleteUserAttendedGame(uage);   //logout old user

                        List<HrajUserEntity> assignedUsers = userAttendedGameDAO.getUsersByGameId(game.getId());
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
                            //TODO let newUser know, that he is ordinary player now
                        }
                    }
                }
            }
        }
        else {
            //TODO error page: you have to log in first
        }
        return "redirect:/game/detail";
    }

    /**
     * This method saves image file from form
     * @param imageFile
     * @param context
     * @param gameName
     * @return
     */
    private String saveFile(CommonsMultipartFile[] imageFile, ServletContext context, String gameName){
        try {
            String path = null;

            if (imageFile != null && imageFile.length > 0 && !imageFile[0].getOriginalFilename().equals("")) {    // there is at least one file attached

                CommonsMultipartFile cmFile = imageFile[0];  // consider only the first attached file

                /* create directories if necessary */
                boolean dirsExists = true;
                File dir = new File(context.getRealPath("assets/img/upload/"));
                if(!(dirsExists = dir.exists()))
                   if(!(dirsExists = dir.mkdirs()))
                       System.out.println("Files could not be created!");

                /* copy attached file into new file on given path */
                if(dirsExists){
                    path = (context.getRealPath("assets/img/upload/") + "/" + gameName + "_" + System.currentTimeMillis() + ".jpg");
                    cmFile.transferTo(new File(path));
                }
            }
            return path;
        }
        catch (Exception e){
            System.out.println("Cant upload file!");
            return null;
        }
    }
}
