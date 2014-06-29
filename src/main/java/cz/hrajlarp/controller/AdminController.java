package cz.hrajlarp.controller;

import cz.hrajlarp.dao.UserAttendedGameDAO;
import cz.hrajlarp.dao.UserIsEditorDAO;
import cz.hrajlarp.dto.PlayerDto;
import cz.hrajlarp.entity.Game;
import cz.hrajlarp.entity.HrajUser;
import cz.hrajlarp.entity.UserAttendedGame;
import cz.hrajlarp.entity.UserIsEditor;
import cz.hrajlarp.service.GameService;
import cz.hrajlarp.service.PlayerService;
import cz.hrajlarp.service.RightsService;
import cz.hrajlarp.service.UserService;
import cz.hrajlarp.service.admin.GameAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Controller
public class AdminController {
    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserIsEditorDAO userIsEditorDAO;
    @Autowired
    private UserAttendedGameDAO userAttendedGameDAO;
    @Autowired
    private RightsService rightsService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private GameAdminService gameAdminService;

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

    @RequestMapping(value = "/admin/game/list", method= RequestMethod.GET)
    public String gameList(
            Model model
    ) {
        HrajUser user = rightsService.getLoggedUser();

        if (rightsService.isEditor(user) || rightsService.isAdministrator(user)){
            gameAdminService.showCalendar(user, model);
            return "/admin/game/list";
        } else {
            model.addAttribute("path", "/admin/game/list");
            return "/admin/norights";
        }
    }

    @RequestMapping(value = "/admin/game/confirmation/{id}", method = RequestMethod.GET)
    public String deleteConfirmation(
            Model model,
            @PathVariable("id") Integer id
    ) {
        model.addAttribute("gameId", id);
        return "/admin/game/confirmation";
    }

    @RequestMapping(value = "/admin/game/delete/{id}", method = RequestMethod.GET)
    public String deleteGame(
            Model model,
            @PathVariable("id") Integer id
    ) {
        HrajUser user = rightsService.getLoggedUser();
        Game game = gameService.getGameById(id);

        if (game!=null && rightsService.hasRightsToEditGame(user, game)){
            gameService.deleteGame(game);
            return "/admin/game/deleted";
        } else {
            model.addAttribute("path", "/admin/game/delete/" + String.valueOf(id));
            return "/admin/norights";
        }
    }

    @RequestMapping(value = "/admin/game/logout/{gameId}/{playerId}", method= RequestMethod.POST)
    public String logoutPlayer(
            Model model,
            @PathVariable("gameId") Integer gameId,
            @PathVariable("playerId") Integer playerId,
            RedirectAttributes redirectAttributes
    ) {
        HrajUser user = rightsService.getLoggedUser();
        Game game = gameService.getGameById(gameId);

        if (rightsService.hasRightsToEditGame(user, game)){
            userService.logoutUser(user, game);

            redirectAttributes.addAttribute("id",gameId);
            return "redirect:/admin/game/players/{id}";
        } else {
            model.addAttribute("path", "/admin/game/logout/" + String.valueOf(gameId) + "/" + playerId);
            return "/admin/norights";
        }
    }

    @RequestMapping(value = "/admin/game/validate/{gameId}")
    public String validateGame(
            Model model,
            @PathVariable("gameId") Integer gameId
    ) {
        HrajUser user = rightsService.getLoggedUser();
        if (rightsService.isAdministrator(user)){
            gameService.approveGame(gameId);
            return "redirect:/admin/game/list";
        } else {
            model.addAttribute("path", "/admin/game/validate/" + String.valueOf(gameId));
            return "/admin/norights";
        }
    }

    @RequestMapping(value = "/admin/game/actions", method= RequestMethod.GET)
    public String actionsShow(
            Model model,
            @ModelAttribute("succesMessage") String succesMessage
    ) {
        if (rightsService.isLoggedAdministrator()){
            List<String> actions = gameService.getAllActions();
            model.addAttribute("actions", actions);
            List<Game> games = gameService.getFutureGames();
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

    /**
     * Every game can belong to some action. Example of such action is for example LarpWeekend.
     * This method simply adds some action to given game, if user has rights to get there.
     *
     * @param model Model for the page
     * @param newAction Action to be added.
     * @param gameIds Ids of the games which should be part of this action.
     * @return If successful returns path to the page containing info about added action.
     */
    @RequestMapping(value = "/admin/game/addAction", method= RequestMethod.POST)
    public String addAction(
            Model model,
            @RequestParam("actionText") String newAction,
            @RequestParam("gameText") List<Integer> gameIds
    ) {
        if (rightsService.isLoggedAdministrator()){
            for (Integer aGameId : gameIds) {
                Game game = gameService.getGameById(aGameId);
                if (game != null && !newAction.isEmpty()) {
                    game.setAction(newAction);
                    gameService.editGame(game);
                    String succesMessage = "succes";
                    model.addAttribute("succesMessage", succesMessage);
                } else {
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
    public String gameEditors(
            Model model,
            @PathVariable("id") Integer id
    ) {
        if (rightsService.isLoggedAdministrator()){
            List<HrajUser> editors = userIsEditorDAO.getEditorsByGameId(id);

            List<HrajUser> allUsers = userService.listUsers();
            List<HrajUser> notEditors = new ArrayList<>();
            for (HrajUser user : allUsers){
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
    public String gameEditorAdd(
            @RequestParam(value = "futureEditors", required = false) List<Integer> editorsIds,
            @PathVariable("gameId") Integer gameId,
            RedirectAttributes redirectAttributes
    ){

        redirectAttributes.addAttribute("id",gameId);

        if(editorsIds == null) {
            return "redirect:/admin/game/editors/{id}";
        }

        for (Integer userId : editorsIds) {
            UserIsEditor userIsEditor = new UserIsEditor();
            userIsEditor.setUserId(userId);
            userIsEditor.setGameId(gameId);
            userIsEditorDAO.saveOrUpdate(userIsEditor);
        }
        return "redirect:/admin/game/editors/{id}";
    }

    @RequestMapping(value = "/admin/game/editors/remove/{gameId}/{userId}", method= RequestMethod.POST)
    public String gameEditorRemove(
            @PathVariable("gameId") Integer gameId,
            @PathVariable("userId") Integer userId,
            RedirectAttributes redirectAttributes
    ){

        UserIsEditor userIsEditor = new UserIsEditor();
        userIsEditor.setUserId(userId);
        userIsEditor.setGameId(gameId);
        userIsEditorDAO.makeTransient(userIsEditor);

        redirectAttributes.addAttribute("id",gameId);
        return "redirect:/admin/game/editors/{id}";
    }


    @RequestMapping(value = "/admin/rights/edit", method= RequestMethod.GET)
    public String rightsEdit(
            Model model
    ) {
        if (rightsService.isLoggedAdministrator()){
            model.addAttribute("admins", userService.getAdmins());
            model.addAttribute("futureAdmins", userService.getUsers());
            model.addAttribute("authEditors", userService.getAuthEditors());

            return "admin/rights/edit";
        }
        model.addAttribute("path", "/admin/rights/edit/");
        return "/admin/norights";
    }

    @RequestMapping(value = "/admin/rights/admins/add", method= RequestMethod.POST)
    public String addAdminsRights(
            @RequestParam(value = "users", required = false) List<Integer> adminsIds
    ){
        if(adminsIds == null) {
            return "redirect:/admin/rights/edit";
        }

        userService.createAdministrators(adminsIds);
        return "redirect:/admin/rights/edit";
    }

    @RequestMapping(value = "/admin/rights/admins/remove/{userId}", method= RequestMethod.POST)
    public String removeAdminsRights(
            @PathVariable("userId") Integer userId
    ){
        if(userService.getAdmins().size() <= 1) {
            return "/admin/rights/error";
        }

        userService.removeAdministrator(userId);
        return "redirect:/admin/rights/edit";
    }

    @RequestMapping(value = "/admin/rights/autheditors/add", method= RequestMethod.POST)
    public String addAuthEditorRights(
            @RequestParam(value = "users", required = false) List<Integer> editorIds
    ){
        if(editorIds == null) {
            return "redirect:/admin/rights/edit";
        }

        userService.addAuthorizedEditors(editorIds);
        return "redirect:/admin/rights/edit";
    }

    @RequestMapping(value = "/admin/rights/autheditors/remove/{userId}", method= RequestMethod.POST)
    public String removeAuthEditorRights(
            @PathVariable("userId") Integer userId
    ){
        userService.removeAuthorizedEditor(userId);
        return "redirect:/admin/rights/edit";
    }

    @RequestMapping(value = "/admin/rights/admins/add/confirm", method= RequestMethod.POST)
    public String addAdminsRightsConfirm(
            Model model,
            @RequestParam(value = "futureAdmins", required = false) List<Integer> adminsIds){
        if(adminsIds == null) {
            return "redirect:/admin/rights/edit";
        }

        List<HrajUser> futureAdmins = new ArrayList<>();
        for (Integer userId : adminsIds){
            futureAdmins.add(userService.getUserById(userId));
        }
        model.addAttribute("users", futureAdmins);
        model.addAttribute("title", "Přidat právo administrátora");
        model.addAttribute("description", "Administrátor má právo schvalovat hry, " +
                "přidávat a odebírat práva dalším administrátorům, přidávat a odebírat práva osvědčeným editorům " +
                "a přidávat a odebírat práva editorům dané hry. V jeho pravomoci je také odhlašovat hráče ze hry " +
                "a schvalovat platby účastníků her. Skutečně si přejete výše uvedeným osobám přidat právo administrátora?");
        model.addAttribute("confirmLink", "/admin/rights/admins/add");
        return "admin/rights/confirmation";
    }

    @RequestMapping(value = "/admin/rights/autheditors/add/confirm", method= RequestMethod.POST)
    public String addAuthEditorRightsConfirm(Model model,
                                @RequestParam(value = "futureAuthorized", required = false) List<Integer> editorIds){
        if(editorIds == null) {
            return "redirect:/admin/rights/edit";
        }

        List<HrajUser> futureAuthEditors = new ArrayList<>();
        for (Integer userId : editorIds){
            futureAuthEditors.add(userService.getUserById(userId));
        }
        model.addAttribute("users", futureAuthEditors);
        model.addAttribute("title", "Přidat právo osvědčeného editora");
        model.addAttribute("description", "Osvědčený editor má právo vkládat hry přímo " +
                "do kalendáře akcí bez nutnosti schválení hry některým z administrátorů. " +
                "Skutečně si přejete výše uvedeným osobám přidat právo osvědčeného editora?");
        model.addAttribute("confirmLink", "/admin/rights/autheditors/add");
        return "admin/rights/confirmation";
    }

    @RequestMapping(value = "/admin/rights/admins/remove/confirm/{userId}", method= RequestMethod.POST)
    public String removeAdminsRightsConfirm(
            Model model,
            @PathVariable("userId") Integer userId
    ){
        List<HrajUser> userList = new ArrayList<>();
        userList.add(userService.getUserById(userId));
        model.addAttribute("users", userList);
        model.addAttribute("title", "Odebrat právo administrátora");
        model.addAttribute("description", "Skutečně má být této osobě odebráno právo administrátora?");
        model.addAttribute("confirmLink", ("/admin/rights/admins/remove/" + userId));
        return "admin/rights/confirmation";
    }

    @RequestMapping(value = "/admin/rights/autheditors/remove/confirm/{userId}", method= RequestMethod.POST)
    public String removeAuthEditorRightsConfirm(
            Model model,
            @PathVariable("userId") Integer userId
    ){
        List<HrajUser> userList = new ArrayList<>(1);
        userList.add(userService.getUserById(userId));
        model.addAttribute("users", userList);
        model.addAttribute("title", "Odebrat právo osvědčeného editora");
        model.addAttribute("description", "Skutečně má být této osobě odebráno právo osvědčeného editora?");
        model.addAttribute("confirmLink", ("/admin/rights/autheditors/remove/" + userId));
        return "admin/rights/confirmation";
    }
}