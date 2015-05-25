package cz.hrajlarp.controller;

import cz.hrajlarp.model.entity.HrajUserEntity;
import cz.hrajlarp.model.dao.UserAttendedGameDAO;
import cz.hrajlarp.model.entity.UserAttendedGameEntity;
import cz.hrajlarp.model.dao.UserDAO;
import cz.hrajlarp.utils.HashString;
import cz.hrajlarp.service.MailService;
import cz.hrajlarp.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 6.3.13
 * Time: 22:14
 */
@Controller
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserAttendedGameDAO userAttendedGameDAO;
    
    @Autowired
    private MailService mailService;

    /**
     * Redirects to new user registration page.
     */
    @RequestMapping(value = "/user/add")
    public String add(Model model) {
        HrajUserEntity user = new HrajUserEntity();
        model.addAttribute("userForm", user);
        return "user/add";
    }

    /**
     * Called after registration form is send to server.
     * Transfers data from reg. form into UserValidator for validation.
     * If everything is ok, saves data into db, else redirects back to reg. page.
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("userForm") HrajUserEntity user, BindingResult result,
                           Model model) {
        new UserValidator().validate(user, result);

        if (!userDAO.userNameIsUnique(user.getUserName()))
            result.rejectValue("userName", "userName is not unique in database",
                    "Uživatelské jméno už je zabrané, vyberte si prosím jiné.");

        if (result.hasErrors()) return "user/add";

        try {
        	HashString hs = new HashString();
            String hashPass = hs.digest(user.getPassword());
            user.setPassword(hashPass);
            user.setActivationLink(hs.digest(user.getEmail() + UUID.randomUUID()));
        } catch (Exception e) {
            return "user/failed";
        }

        user.setActivated(false);
        userDAO.addUser(user);
        mailService.sendActivation(user);
        model.addAttribute("info", "Registrace probìhla úspìšnì. Na zadanou e-mailovou adresu byl odeslán ovìøovací mail." +
        		"Zkontrolujte si prosím Vaši schránku. Mùžete se pøihlásit ke svému úètu. Uživatel s neovìøenou adresou má omezené možnosti.");
        return "user/login";
    }

    /**
     * Loads user data from DB and prepares them for viewing in edit page form.
     */
    @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
    public String edit(Model model, @RequestParam(value = "id", required = false) Integer id) {
        model.addAttribute("userForm", new HrajUserEntity());

        HrajUserEntity user = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HrajUserEntity logged = userDAO.getUserByLogin(auth.getName());

        if (id != null) user = userDAO.getUserById(id);

        /* rights for admins might be tested here */
        if (logged == null || (user != null && !user.equals(logged)))
            return "/error";   // attempt to edit foreign user account

        if (logged.getGender() != null)
            logged.setGenderForm((logged.getGender() == 0) ? "M" : "F");

        logged.setOldPassword(logged.getPassword());
        logged.setPassword("");
        model.addAttribute("userForm", logged);
        return "user/edit";
    }

    /**
     * Called after user updates his profile.
     * Transfers data from update form into UserValidator for validation.
     * If everything is ok, saves data into db, else redirects back to edit page.
     */
    @RequestMapping(value = "/user/update", method = RequestMethod.POST)
    public String update(@ModelAttribute("userForm") HrajUserEntity user, BindingResult result,Model model) {

        new UserValidator().validateEditedProfile(user, result);
        if (result.hasErrors()) return "user/edit";
        user.setGender(userDAO.getUserById(user.getId()).getGender());
        try {
            if (user.getPassword() == null || user.getPassword().trim().equals("")) {
                user.setPassword(user.getOldPassword());
            } else {
                String hashPass = new HashString().digest(user.getPassword());
                user.setPassword(hashPass);
            }
        } catch (Exception e) {
            return "user/failed";
        }

        if(!userDAO.getUserById(user.getId()).getEmail().equals(user.getEmail())){//mail address edited
        	try {
            	HashString hs = new HashString();
                user.setActivationLink(hs.digest(user.getEmail() + UUID.randomUUID()));
            } catch (Exception e) {
                return "user/failed";
            }
        	user.setActivated(false);
        	mailService.sendActivation(user);
        	model.addAttribute("mailEdited", true);
        }else{
        	HrajUserEntity oldUserData = userDAO.getUserById(user.getId());
        	user.setActivated(oldUserData.getActivated());
        	user.setActivationLink(oldUserData.getActivationLink());
        }

        userDAO.editUser(user);

        return "user/success";
    }

    /**
     * Method creates the overview of games attended by user (former and future)
     *
     * @param model
     * @param id    user id
     * @return
     */
    @RequestMapping(value = "/user/attended", method = RequestMethod.GET)
    public String userAttended(Model model,
                               @RequestParam(value = "id", required = false) Integer id) {

        HrajUserEntity user = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HrajUserEntity logged = userDAO.getUserByLogin(auth.getName());

        if (id != null) user = userDAO.getUserById(id);

        /* rights for admins might be tested here */
        if (logged == null || (user != null && !user.equals(logged)))
            return "/error";   // attempt to view games attended by other user

        List<UserAttendedGameEntity> attendedFormer = userAttendedGameDAO.getAttendedFormer(logged);
        List<UserAttendedGameEntity> attendedFuture = userAttendedGameDAO.getAttendedFuture(logged);

        model.addAttribute("futureGames", attendedFuture);
        model.addAttribute("formerGames", attendedFormer);
        return "/user/attended";
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public String login(Model model) {
        return "user/login";
    }

    @RequestMapping(value = "/user/loginfailed", method = RequestMethod.GET)
    public String failed(Model model) {
        model.addAttribute("info", "Zadané jméno nebo heslo neexistuje. Zkuste to znovu.");
        return "user/login";
    }
    
    @RequestMapping(value = "/user/activation/{activationLink}", method= RequestMethod.GET)
    public String activation(Model model, @PathVariable("activationLink") String activationLink) {
    	HrajUserEntity u = userDAO.getUserToActivate(activationLink);
    	if (u!=null){ 
    		u.setActivated(true);
	    	userDAO.editUser(u);
	        return "/user/activated";
    	}else
    		return "/user/failed";
    }

}
