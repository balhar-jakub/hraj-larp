package cz.hrajlarp.controller;

import cz.hrajlarp.model.Game;
import cz.hrajlarp.model.GameDAO;
import cz.hrajlarp.model.GameEntity;
import cz.hrajlarp.model.ValidGame;
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

    /**
     * Basic view of add game form
     * @param model
     * @return
     */
    @RequestMapping(value = "/game/add", method= RequestMethod.GET, produces="text/plain;charset=UTF-8")
    public String showForm(ModelMap model){
        model.addAttribute("myGame", new ValidGame());
        return "game/add";
    }

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

        String image = saveFile(imageFile, request.getSession().getServletContext(), "gameName");
        myGame.setImage(image);
        myGame.validate(r);
        if (r.hasErrors()) return "game/add";

        GameEntity game = myGame.getGameEntity();
        gameDAO.addGame(game);
        System.out.println("Formular odeslan");
        return "/game/added";
    }

    /**
     * Method for view of game detail
     * @param gameId id of the game to view
     * @param model model
     * @return  String of .JSP file for game detail view
     */
    @RequestMapping(value="/game/detail", method= RequestMethod.GET)
    public String detail(@RequestParam("id") String gameId, Model model) {
        System.out.println("GameController: Passing through..." + "/game/detail" );

        if(gameId != null && !gameId.isEmpty()){
            try{
                int id = Integer.parseInt(gameId);
                if(id <= 0)
                    throw new Exception();

                Game game = gameDAO.getGameById(id);
                model.addAttribute("game", game);

            }catch(Exception e){

                /* TODO error message is too brief and not styled in .JSP file*/
                model.addAttribute("error", "Hra #" + gameId + " nebyla nalezena");
            }
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
        try {
            int intId = Integer.parseInt(id);
            if (intId < 0) return "game/error";
            GameEntity game = gameDAO.findGame(intId);
            if (game != null) {
                model.addAttribute("game", game);
                model.addAttribute("date", game.getDate().toString().substring(0, 10));
                model.addAttribute("time", game.getDate().toString().substring(11,16));
                return "game/edit";
            }
            else {
                return "game/error";
            }
        }
        catch (NumberFormatException e) {
            return "game/error";
        }

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
            @ModelAttribute("myGame") ValidGame myGame,
            BindingResult r
            ){

        //TODO image editation
        myGame.setImage("img.jpg");  //dump fix untill image editation will be done
        myGame.validate(r);
        if (r.hasErrors()) return "game/add";

        GameEntity game = myGame.getGameEntity();
        gameDAO.editGame(game);
        System.out.println("Formular odeslan");
        return "/game/added";
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
