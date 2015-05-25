package cz.hrajlarp.controller;

import cz.hrajlarp.model.Rights;
import cz.hrajlarp.model.dao.*;
import cz.hrajlarp.model.entity.*;
import cz.hrajlarp.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    private UserIsEditorDAO userIsEditorDAO;

    @Autowired
    private UserAttendedGameDAO userAttendedGameDAO;

    @Autowired
    private MailService mailService;

    @Autowired
    private Rights rights;

    @Autowired
    private AdministratorDAO administratorDAO;

    @Autowired
    private AuthorizedEditorDAO authorizedEditorDAO;

    @RequestMapping(value = "/admin/game/players/{id}", method= RequestMethod.GET)
    @Transactional
    public String gamePlayers(Model model, @PathVariable("id") Integer id) {
        HrajUserEntity user = rights.getLoggedUser();
        GameEntity game = gameDAO.getGameById(id);

        if (rights.hasRightsToEditGame(user, game)){
            List<UserAttendedGameEntity> players =
                    userAttendedGameDAO.getPlayers(id, false);
            List<UserAttendedGameEntity> substitutes =
                    userAttendedGameDAO.getPlayers(id, true);

            model.addAttribute("gameId",id);
            model.addAttribute("paymentFinished", game.getPaymentFinished() != null && game.getPaymentFinished());
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

    @RequestMapping(value = "/admin/game/finished/{id}")
    @Transactional
    public String paymentFinished(Model model, @PathVariable("id") Integer id) {
        HrajUserEntity user = rights.getLoggedUser();
        GameEntity game = gameDAO.getGameById(id);

        game.setPaymentFinished(true);
        gameDAO.editGame(game);

        List<UserAttendedGameEntity> players =
                userAttendedGameDAO.getPlayers(id, false);
        List<UserAttendedGameEntity> substitutes =
                userAttendedGameDAO.getPlayers(id, true);

        model.addAttribute("gameId",id);
        model.addAttribute("paymentFinished",
                game.getPaymentFinished() != null && game.getPaymentFinished());
        model.addAttribute("gameName", game.getName());
        model.addAttribute("players", players);
        model.addAttribute("substitutes", substitutes);
        model.addAttribute("isLogged", true);
        return "/admin/game/players";
    }

    @RequestMapping(value="/admin/user/payed/{gameId}/{userId}")
    @Transactional
    public String gamePayment(Model model,
                              @PathVariable("gameId") Integer gameId,
                              @PathVariable("userId") Integer userId,
                              RedirectAttributes redirectAttributes) {
        HrajUserEntity user = rights.getLoggedUser();
        GameEntity game = gameDAO.getGameById(gameId);
        if (rights.hasRightsToEditGame(user, game)){
            UserAttendedGameEntity payingPlayer = userAttendedGameDAO.getLogged(gameId, userId);
            if(payingPlayer != null){
                if(payingPlayer.getPayed() == null || !payingPlayer.getPayed()){
                    payingPlayer.setPayed(true);
                } else {
                    payingPlayer.setPayed(false);
                }
            }
            userAttendedGameDAO.editUserAttendedGame(payingPlayer);

            redirectAttributes.addAttribute("id",gameId);
            return "redirect:/admin/game/players/{id}";
        } else {
            model.addAttribute("path", "/admin/user/payed/" +
                    String.valueOf(gameId) + "/" + String.valueOf(userId));
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
            model.addAttribute("isAdmin", rights.isAdministrator(user));
            return "/admin/game/list";
        } else {
            model.addAttribute("path", "/admin/game/list");
            return "/admin/norights";
        }
    }

    @RequestMapping(value = "/admin/game/confirmation/{id}", method = RequestMethod.GET)
    public String deleteConfirmation(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("gameId", id);
        return "/admin/game/confirmation";
    }

    @RequestMapping(value = "/admin/game/delete/{id}", method = RequestMethod.GET)
    @Transactional
    public String deleteGame(Model model, @PathVariable("id") Integer id) {
        HrajUserEntity user = rights.getLoggedUser();
        GameEntity game = gameDAO.getGameById(id);

        if (game!=null && rights.hasRightsToEditGame(user, game)){
            List<UserAttendedGameEntity> records = userAttendedGameDAO.getRecordsByGameId(id);
            StringBuilder sb = new StringBuilder("Došlo ke zrušení hry " + game.getName() + ". Hra byla naplánovaná na "
            		+ game.getDateAsDMY()+ "\n\nSeznam pøihlášených hráèù:\n");
            for(UserAttendedGameEntity uage : records){
            	HrajUserEntity u = userDAO.getUserById(uage.getUserId());
            	sb.append("JMÉNO: " + u.getName() +" "+ u.getLastName() + "\t LOGIN: "+u.getUserName()
            			+ "\t PLATIL: "+ uage.getPayedTextual() +"\n");
                userAttendedGameDAO.deleteUserAttendedGame(uage);
            }
            mailService.notifyAdminOnGameDeletion(sb.toString());
            List<HrajUserEntity> editors = userIsEditorDAO.getEditorsByGameId(game.getId());
            if (editors != null) {
                for(HrajUserEntity editor : editors){
                    UserIsEditorEntity userIsEditor = (UserIsEditorEntity)userIsEditorDAO.getUserIsEditor(editor, game);
                    userIsEditorDAO.deleteUserIsEditor(userIsEditor);
                }
            }
            gameDAO.deleteGame(game);
            return "/admin/game/deleted";
        } else {
            model.addAttribute("path", "/admin/game/delete/" + String.valueOf(id));
            return "/admin/norights";
        }
    }

    @RequestMapping(value = "/admin/game/logout/{gameId}/{playerId}", method= RequestMethod.POST)
    @Transactional
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

                try{
                    game.countPlayers(userAttendedGameDAO);   //count new free roles count
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

    @RequestMapping(value = "/admin/game/actions", method= RequestMethod.GET)
    public String actionsShow(Model model,
                                @ModelAttribute("succesMessage") String succesMessage) {
        HrajUserEntity user = rights.getLoggedUser();

        if (rights.isAdministrator(user)){
            List<String> actions = gameDAO.getAllActions();
            model.addAttribute("actions", actions);
            List<GameEntity> games = gameDAO.getFutureGames();
            model.addAttribute("games", games);
            if (!succesMessage.isEmpty()){
                if (succesMessage.equals("succes")) model.addAttribute("succesMessage", true);
                else model.addAttribute("succesMessage", false);
            }
            return "/admin/game/actions";
        } else {
            model.addAttribute("path", "/admin/game/actions/");
            return "/admin/norights";
        }
    }

    @RequestMapping(value = "/admin/game/addAction", method= RequestMethod.POST)
    @Transactional
    public String addAction(Model model,
                            @RequestParam("actionText") String newAction,
                            @RequestParam("gameText") List<Integer> gameId) {
        HrajUserEntity user = rights.getLoggedUser();

        if (rights.isAdministrator(user)){
            for (int i = 0; i < gameId.size(); i++){
                GameEntity game = gameDAO.getGameById(gameId.get(i));
                if (game != null && !newAction.isEmpty()){
                    game.setAction(newAction);
                    gameDAO.editGame(game);
                    String succesMessage = "succes";
                    model.addAttribute("succesMessage", succesMessage);
                }
                else {
                    String succesMessage = "error";
                    model.addAttribute("succesMessage", succesMessage);
                }
            }
            return "redirect:/admin/game/actions";
        } else {
            model.addAttribute("path", "/admin/game/actions/");
            return "/admin/norights";
        }
    }


    @RequestMapping(value = "/admin/game/editors/{id}", method= RequestMethod.GET)
    @Transactional
    public String gameEditors(Model model, @PathVariable("id") Integer id) {

        if (rights.isLoggedAdministrator()){
            List<HrajUserEntity> editors = userIsEditorDAO.getEditorsByGameId(id);

            List<HrajUserEntity> allUsers = userDAO.listUsers();
            List<HrajUserEntity> notEditors = new ArrayList<HrajUserEntity>();
            for (HrajUserEntity user : allUsers){
                if(!editors.contains(user))
                    notEditors.add(user);
            }

            model.addAttribute("gameId",id);
            model.addAttribute("editors", editors);
            model.addAttribute("notEditors", notEditors);
            model.addAttribute("isLogged", true);
            return "/admin/game/editors";
        } else {
            model.addAttribute("path", "/admin/game/editors/" + String.valueOf(id));
            return "/admin/norights";
        }
    }

    @RequestMapping(value = "/admin/game/editors/add/{gameId}", method= RequestMethod.POST)
    public String gameEditorAdd(Model model,
                                @RequestParam(value = "futureEditors", required = false) List<Integer> editorsIds,
                                @PathVariable("gameId") Integer gameId,
                                RedirectAttributes redirectAttributes){

        redirectAttributes.addAttribute("id",gameId);

        if(editorsIds == null)
            return "redirect:/admin/game/editors/{id}";

        for (Integer userId : editorsIds) {
            UserIsEditorEntity userIsEditorEntity = new UserIsEditorEntity();
            userIsEditorEntity.setUserId(userId);
            userIsEditorEntity.setGameId(gameId);
            userIsEditorDAO.addUserIsEditor(userIsEditorEntity);
        }
        return "redirect:/admin/game/editors/{id}";
    }

    @RequestMapping(value = "/admin/game/editors/remove/{gameId}/{userId}", method= RequestMethod.POST)
    public String gameEditorRemove(Model model,
                               @PathVariable("gameId") Integer gameId,
                               @PathVariable("userId") Integer userId,
                               RedirectAttributes redirectAttributes){

        UserIsEditorEntity userIsEditorEntity = new UserIsEditorEntity();
        userIsEditorEntity.setUserId(userId);
        userIsEditorEntity.setGameId(gameId);
        userIsEditorDAO.deleteUserIsEditor(userIsEditorEntity);

        redirectAttributes.addAttribute("id",gameId);
        return "redirect:/admin/game/editors/{id}";
    }


    @RequestMapping(value = "/admin/rights/edit", method= RequestMethod.GET)
    public String rightsEdit(Model model) {

        if (rights.isLoggedAdministrator()){
            List<Integer> allAdmins = administratorDAO.getAdministratorIds();
            List<Integer> allEditors = authorizedEditorDAO.listAuthorizedEditors();

            List<HrajUserEntity> allUsers = userDAO.listUsers();

            List<HrajUserEntity> admins = new ArrayList<HrajUserEntity>();
            List<HrajUserEntity> notAdmins = new ArrayList<HrajUserEntity>();

            List<HrajUserEntity> authEditors = new ArrayList<HrajUserEntity>();
            List<HrajUserEntity> notAuthEditors = new ArrayList<HrajUserEntity>();

            for (HrajUserEntity user : allUsers){
                if(allAdmins.contains(user.getId()))
                    admins.add(user);
                else
                    notAdmins.add(user);

                if(allEditors.contains(user.getId()))
                    authEditors.add(user);
                else
                    notAuthEditors.add(user);
            }

            model.addAttribute("admins", admins);
            model.addAttribute("futureAdmins", notAdmins);
            model.addAttribute("authEditors", authEditors);
            model.addAttribute("futureAuthorized", notAuthEditors);

            return "admin/rights/edit";
        }
        model.addAttribute("path", "/admin/rights/edit/");
        return "/admin/norights";
    }

    @RequestMapping(value = "/admin/rights/admins/add", method= RequestMethod.POST)
    public String addAdminsRights(Model model,
                                @RequestParam(value = "users", required = false) List<Integer> adminsIds){

        if(adminsIds == null)
            return "redirect:/admin/rights/edit";

        for (Integer userId : adminsIds) {
            administratorDAO.setAdministrator(userId);
        }
        return "redirect:/admin/rights/edit";
    }

    @RequestMapping(value = "/admin/rights/admins/remove/{userId}", method= RequestMethod.POST)
    public String removeAdminsRights(Model model,
                               @PathVariable("userId") Integer userId){

        if(administratorDAO.getAdministratorIds().size() <= 1)
        return "/admin/rights/error";

        administratorDAO.removeAdministratorRights(userId);
        return "redirect:/admin/rights/edit";
    }

    @RequestMapping(value = "/admin/rights/autheditors/add", method= RequestMethod.POST)
    public String addAuthEditorRights(Model model,
                                @RequestParam(value = "users", required = false) List<Integer> editorIds){

        if(editorIds == null)
            return "redirect:/admin/rights/edit";

        for (Integer userId : editorIds) {
            authorizedEditorDAO.setAuthorizedEntity(userId);
        }
        return "redirect:/admin/rights/edit";
    }

    @RequestMapping(value = "/admin/rights/autheditors/remove/{userId}", method= RequestMethod.POST)
    public String removeAuthEditorRights(Model model,
                               @PathVariable("userId") Integer userId){

        authorizedEditorDAO.removeAuthorizedEntityRights(userId);
        return "redirect:/admin/rights/edit";
    }



    @RequestMapping(value = "/admin/rights/admins/add/confirm", method= RequestMethod.POST)
    public String addAdminsRightsConfirm(Model model,
                                @RequestParam(value = "futureAdmins", required = false) List<Integer> adminsIds){
        if(adminsIds == null)
            return "redirect:/admin/rights/edit";

        List<HrajUserEntity> futureAdmins = new ArrayList<HrajUserEntity>();
        for (Integer userId : adminsIds){
            futureAdmins.add(userDAO.getUserById(userId));
        }
        model.addAttribute("users", futureAdmins);
        model.addAttribute("title", "Pøidat právo administrátora");
        model.addAttribute("description", "Administrátor má právo schvalovat hry, " +
                "pøidávat a odebírat práva dalším administrátorùm, pøidávat a odebírat práva osvìdèeným editorùm " +
                "a pøidávat a odebírat práva editorùm dané hry. V jeho pravomoci je také odhlašovat hráèe ze hry " +
                "a schvalovat platby úèastníkù her. Skuteènì si pøejete výše uvedeným osobám pøidat právo administrátora?");
        model.addAttribute("confirmLink", "/admin/rights/admins/add");
        return "admin/rights/confirmation";
    }

    @RequestMapping(value = "/admin/rights/autheditors/add/confirm", method= RequestMethod.POST)
    public String addAuthEditorRightsConfirm(Model model,
                                @RequestParam(value = "futureAuthorized", required = false) List<Integer> editorIds){
        if(editorIds == null)
            return "redirect:/admin/rights/edit";

        List<HrajUserEntity> futureAuthEditors = new ArrayList<HrajUserEntity>();
        for (Integer userId : editorIds){
            futureAuthEditors.add(userDAO.getUserById(userId));
        }
        model.addAttribute("users", futureAuthEditors);
        model.addAttribute("title", "Pøidat právo osvìdèeného editora");
        model.addAttribute("description", "Osvìdèený editor má právo vkládat hry pøímo " +
                "do kalendáøe akcí bez nutnosti schválení hry nìkterým z administrátorù. " +
                "Skuteènì si pøejete výše uvedeným osobám pøidat právo osvìdèeného editora?");
        model.addAttribute("confirmLink", "/admin/rights/autheditors/add");
        return "admin/rights/confirmation";
    }

    @RequestMapping(value = "/admin/rights/admins/remove/confirm/{userId}", method= RequestMethod.POST)
    public String removeAdminsRightsConfirm(Model model,
                               @PathVariable("userId") Integer userId){

        List<HrajUserEntity> userList = new ArrayList<HrajUserEntity>();
        userList.add(userDAO.getUserById(userId));
        model.addAttribute("users", userList);
        model.addAttribute("title", "Odebrat právo administrátora");
        model.addAttribute("description", "Skuteènì má být této osobì odebráno právo administrátora?");
        model.addAttribute("confirmLink", ("/admin/rights/admins/remove/" + userId));
        return "admin/rights/confirmation";
    }

    @RequestMapping(value = "/admin/rights/autheditors/remove/confirm/{userId}", method= RequestMethod.POST)
    public String removeAuthEditorRightsConfirm(Model model,@PathVariable("userId") Integer userId){

        List<HrajUserEntity> userList = new ArrayList<HrajUserEntity>(1);
        userList.add(userDAO.getUserById(userId));
        model.addAttribute("users", userList);
        model.addAttribute("title", "Odebrat právo osvìdèeného editora");
        model.addAttribute("description", "Skuteènì má být této osobì odebráno právo osvìdèeného editora?");
        model.addAttribute("confirmLink", ("/admin/rights/autheditors/remove/" + userId));
        return "admin/rights/confirmation";
    }
}
