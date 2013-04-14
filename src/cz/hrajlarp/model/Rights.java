package cz.hrajlarp.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * Created by IntelliJ IDEA.
 * User: Matheo
 * Date: 24.3.13
 * Time: 15:17
 * To change this template use File | Settings | File Templates.
 */
@Service
public class Rights {

    @Autowired
    private UserIsEditorDAO userIsEditorDAO;

    @Autowired
    private AdministratorDAO administratorDAO;

    @Autowired
    private UserDAO userDAO;

    public static enum Role {
        ADMINISTRATOR,      // set editors and assign games to editors, is also EDITOR
        EDITOR,             // can edit assigned games, is also USER_LOGGED
        USER_LOGGED,        // user logged in, can subscribe or unsubscribe himself from games, is also USER_ANONYMOUS
        USER_ANONYMOUS      // no user is logged in, can view standard accessible pages
    }

    /**
     * Method returns current role of the user.
     * Result is not related to any game, it is just the role in the system.
     * Role.USER_ANONYMOUS means that given user is not the logged one.
     * @param user entity to get role
     * @return user role in the system - not related to game
     */
    public Role getUserRole(HrajUserEntity user){
        if(user == null)
            return Role.USER_ANONYMOUS;

        if(administratorDAO.isAdministrator(user))
            return Role.ADMINISTRATOR;

        if(userIsEditorDAO.isEditor(user))
            return Role.EDITOR;

        if(isLoggedUser(user))
            return Role.USER_LOGGED;

        return Role.USER_ANONYMOUS;
    }

    /**
     * Method gets logged user from SecurityContextHolder.
     * @return true, if there is a logged user (not anonymous)
     */
    public boolean isLogged(){
        Authentication a = SecurityContextHolder.getContext().getAuthentication();

        /* anonyomous user would have Principal of type String not UserDetails */
        if(a != null && a.getPrincipal() != null && a.isAuthenticated()
                && a.getPrincipal() instanceof UserDetails){

            /* authentication must hold user, who is in the database */
            return userDAO.getUserByLogin(a.getName()) != null;
        }
        return false;
    }

    /**
     * Test if given user is administrator (in the database).
     * @param user tested user
     * @return true, if user is administrator
     */
    public boolean isAdministrator(HrajUserEntity user){
        return getUserRole(user) == Role.ADMINISTRATOR;
    }

    /**
     * Test if given user is editor (in the database)
     * @param user tested user
     * @return true, if user is editor (administrator is also editor)
     */
    public boolean isEditor(HrajUserEntity user){
        Role userRole = getUserRole(user);
        return userRole == Role.ADMINISTRATOR || userRole == Role.EDITOR;
    }

    /**
     * Gets user from SecurityContextHolder
     * @return logged user or null (if no user is logged)
     */
    public HrajUserEntity getLoggedUser(){
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if(a != null && a.getPrincipal() != null && a.isAuthenticated()
                   && a.getPrincipal() instanceof UserDetails)
            return userDAO.getUserByLogin(a.getName());
        return null;
    }

    /**
     * Test if given user is logged in user in SecurityContextHolder.
     * @param user tested user
     * @return true, if user is logged in
     */
    public boolean isLoggedUser(HrajUserEntity user){
        Authentication a = SecurityContextHolder.getContext().getAuthentication();

        if(a != null && a.getPrincipal() != null && a.isAuthenticated()
                   && a.getPrincipal() instanceof UserDetails){

            HrajUserEntity loggedUser = userDAO.getUserByLogin(a.getName());
            return (user.equals(loggedUser));
        }
        return false;
    }

    /**
     * Test if given user has permission to edit given game
     * @param user tested user
     * @param game tested game
     * @return true, if user has permission to edit game
     */
    public boolean hasRightsToEditGame(HrajUserEntity user, GameEntity game){
         return userIsEditorDAO.isEditorOfGame(user, game) || isAdministrator(user);
    }
}
