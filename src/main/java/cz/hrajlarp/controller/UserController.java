package cz.hrajlarp.controller;

import cz.hrajlarp.dao.UserAttendedGameDAO;
import cz.hrajlarp.dao.UserDAO;
import cz.hrajlarp.entity.HrajUser;
import cz.hrajlarp.entity.UserAttendedGame;
import cz.hrajlarp.service.HashService;
import cz.hrajlarp.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
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
        HrajUser user = new HrajUser();
        model.addAttribute("userForm", user);
        return "user/add";
    }

    /**
     * Called after registration form is send to server.
     * Transfers data from reg. form into UserValidator for validation.
     * If everything is ok, saves data into db, else redirects back to reg. page.
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String register(
            @ModelAttribute("userForm") HrajUser user,
            BindingResult result,
            Model model
    ) {
        //TODO Validate user

        if (!userDAO.userNameIsUnique(user.getUserName()))
            result.rejectValue("userName", "userName is not unique in database",
                    "Uživatelské jméno už je zabrané, vyberte si prosím jiné.");

        if (result.hasErrors()) return "user/add";

        try {
        	HashService hs = new HashService();
            String hashPass = hs.digest(user.getPassword());
            user.setPassword(hashPass);
            user.setActivationLink(hs.digest(user.getEmail()));
        } catch (Exception e) {
            return "user/failed";
        }
        
        user.setActivated(false);
        userDAO.saveOrUpdate(user);
        mailService.sendActivation(user);
        model.addAttribute("info", "Registrace proběhla úspěšně. Na zadanou e-mailovou adresu byl odeslán ověřovací mail." +
        		"Zkontrolujte si prosím Vaši schránku. Můžete se přihlásit ke svému účtu. Uživatel s neověřenou adresou má omezené možnosti.");
        return "user/login";
    }

    /**
     * Loads user data from DB and prepares them for viewing in edit page form.
     */
    @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
    public String edit(Model model, @RequestParam(value = "id", required = false) Integer id) {
        model.addAttribute("userForm", new HrajUser());

        HrajUser user = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // TODO Instead of HrajUser return UserDto.
        HrajUser logged = userDAO.getUserByLogin(auth.getName());

        if (id != null) user = userDAO.findById(id);

        /* rights for admins might be tested here */
        if (logged == null || (user != null && !user.equals(logged)))
            return "/error";   // attempt to edit foreign user account

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
    public String update(@ModelAttribute("userForm") HrajUser user, BindingResult result,Model model) {
        // TODO WOrk with UserDto instead of HrajUser
        if (result.hasErrors()) {
            return "user/edit";
        }
        user.setGender(userDAO.findById(user.getId()).getGender());
        try {
            if (user.getPassword() == null || user.getPassword().trim().equals("")) {
                //user.setPassword(user.getOldPassword());
            } else {
                String hashPass = new HashService().digest(user.getPassword());
                user.setPassword(hashPass);
            }
        } catch (Exception e) {
            return "user/failed";
        }
        
        if(!userDAO.findById(user.getId()).getEmail().equals(user.getEmail())){//mail address edited
        	try {
            	HashService hs = new HashService();
                user.setActivationLink(hs.digest(user.getEmail()));
            } catch (Exception e) {
                return "user/failed";
            }
        	user.setActivated(false);
        	mailService.sendActivation(user);
        	model.addAttribute("mailEdited", true);
        }else{
        	HrajUser oldUserData = userDAO.findById(user.getId());
        	user.setActivated(oldUserData.getActivated());
        	user.setActivationLink(oldUserData.getActivationLink());
        }
        
        userDAO.saveOrUpdate(user);
        
        return "user/success";
    }

    /**
     * Method creates the overview of games attended by user (former and future)
     *
     * @param id    user id
     * @return Path to JSP serving the request.
     */
    @RequestMapping(value = "/user/attended", method = RequestMethod.GET)
    public String userAttended(Model model,
                               @RequestParam(value = "id", required = false) Integer id) {

        HrajUser user = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        HrajUser logged = userDAO.getUserByLogin(auth.getName());

        if (id != null) user = userDAO.findById(id);

        /* rights for admins might be tested here */
        if (logged == null || (user != null && !user.equals(logged)))
            return "/error";   // attempt to view games attended by other user

        List<UserAttendedGame> attendedFormer = userAttendedGameDAO.getAttendedInPast(logged);
        List<UserAttendedGame> attendedFuture = userAttendedGameDAO.getAttendedInFuture(logged);

        model.addAttribute("futureGames", attendedFuture);
        model.addAttribute("formerGames", attendedFormer);
        return "/user/attended";
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public String login() {
        return "user/login";
    }

    @RequestMapping(value = "/user/loginfailed", method = RequestMethod.GET)
    public String failed(Model model) {
        model.addAttribute("info", "Zadané jméno nebo heslo neexistuje. Zkuste to znovu.");
        return "user/login";
    }
    
    @RequestMapping(value = "/user/activation/{activationLink}", method= RequestMethod.GET)
    public String activation(
            @PathVariable("activationLink") String activationLink
    ) {
    	HrajUser user = userDAO.getUserToActivate(activationLink);
    	if (user!=null){
    		user.setActivated(true);
	    	userDAO.saveOrUpdate(user);
	        return "/user/activated";
    	}else
    		return "/user/failed";
    }

}
