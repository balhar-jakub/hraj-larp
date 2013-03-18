package cz.hrajlarp.controller;

import cz.hrajlarp.model.HrajUserEntity;
import cz.hrajlarp.model.UserDAO;
import cz.hrajlarp.utils.HashString;
import cz.hrajlarp.utils.UserValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	/**
     * Redirects to new user registration page.
     */
    @RequestMapping(value="/user/add")
    public String add(Model model){
        HrajUserEntity user = new HrajUserEntity();
        model.addAttribute("userForm", user);
        return "user/add";
    }
    
    /**
     * Called after registration form is send to server.
     * Transfers data from reg. form into UserValidator for validation.
     * If everything is ok, saves data into db, else redirects back to reg. page.
     */
    @RequestMapping(value="/user/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("userForm") HrajUserEntity user, BindingResult result){
        new UserValidator().validate(user, result);
        if (result.hasErrors()) return "user/add";
        
        try{
	        String hashPass = new HashString().digest(user.getPassword());
	        user.setPassword(hashPass);
        }catch (Exception e){
        	return "user/failed";
        }
        
        userDAO.addUser(user);
        return "user/success";
    }
    
    /**
     * Loads user data from DB and prepares them for viewing in edit page form.
     */
    @RequestMapping(value="/user/edit", method= RequestMethod.GET)
    public String edit(Model model, @ModelAttribute("id") String id){
        model.addAttribute("userForm", new HrajUserEntity());
        id="2";//just testing value
        int userId = Integer.parseInt(id);
        HrajUserEntity user = userDAO.getUserById(userId);
        if (user==null) 
        	return "user/failed";
        else {
        	 
             if(user.getGender()!=null){
            	 if(user.getGender().intValue()==0)
            		 user.setGenderForm("M");
            	 else user.setGenderForm("F");
        	}
        	user.setPassword("");
            model.addAttribute("userForm", user);
            return "user/edit";
        }
    }
    
    /**
     * Called after user updates his profile.
     * Transfers data from update form into UserValidator for validation.
     * If everything is ok, saves data into db, else redirects back to edit page.
     */
    @RequestMapping(value="/user/update", method = RequestMethod.POST)
    public String update(@ModelAttribute("userForm") HrajUserEntity user, BindingResult result){
    	
        new UserValidator().validate(user, result);
        if (result.hasErrors()) return "user/edit";
        
        try{
	        String hashPass = new HashString().digest(user.getPassword());
	        user.setPassword(hashPass);
        }catch (Exception e){
        	return "user/failed";
        }
        
        userDAO.editUser(user);
        return "user/success";
    }
    
}

