package cz.hrajlarp.service;

import cz.hrajlarp.dao.UserAttendedGameDAO;
import cz.hrajlarp.dao.UserDAO;
import cz.hrajlarp.entity.Game;
import cz.hrajlarp.entity.HrajUser;
import cz.hrajlarp.entity.UserAttendedGame;
import cz.hrajlarp.enums.HrajRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class UserService {
    @Autowired
    UserAttendedGameDAO userAttendedGameDAO;
    @Autowired
    MailService mailService;
    @Autowired
    UserDAO userDAO;
    @Autowired
    GameService gameService;

    public HrajUser getUserById(int userId) {
        return userDAO.findById(userId);
    }

    public List<HrajUser> listUsers() {
        return userDAO.findAll();
    }

    public List<HrajUser> getAdmins() {
        return userDAO.getAdmins();
    }

    public List<HrajUser> getUsers() {
        return userDAO.getUsers();
    }

    public List<HrajUser> getAuthEditors() {
        return userDAO.getAuthEditors();
    }

    public void removeAdministrator(Integer userId) {
        HrajUser user = userDAO.findById(userId);
        user.setRole(HrajRoles.USER);
        userDAO.saveOrUpdate(user);
    }

    public void addAuthorizedEditors(List<Integer> editorIds) {
        for(Integer editor: editorIds) {
            HrajUser user = userDAO.findById(editor);
            user.setRole(HrajRoles.AUTHORIZED_EDITOR);
            userDAO.saveOrUpdate(user);
        }
    }

    public void removeAuthorizedEditor(Integer userId) {
        HrajUser user = userDAO.findById(userId);
        user.setRole(HrajRoles.USER);
        userDAO.saveOrUpdate(user);
    }

    public void createAdministrators(List<Integer> adminsIds) {
        for(Integer admin: adminsIds) {
            HrajUser user = userDAO.findById(admin);
            user.setRole(HrajRoles.AUTHORIZED_EDITOR);
            userDAO.saveOrUpdate(user);
        }
    }

    public void logoutUser(HrajUser user, Game game) {
        UserAttendedGame uage = userAttendedGameDAO.getSpecificOne(user.getId(), game.getId());
        if (uage != null){   //user is logged in this game
            userAttendedGameDAO.makeTransient(uage);   //logout old user
            /* if logged out user was not just a substitute, some of substitutes should replace him */
            if(!uage.isSubstitute()){
                uage = userAttendedGameDAO.getFirstSubstitutedUAG(game.getId(), uage.getUserAttended().getGender());  //get first substitute according to gender
                if (uage != null) {
                    HrajUser newUser = uage.getUserAttended();
                    uage.setUserId(newUser.getId());
                    uage.setSubstitute(false);
                    userAttendedGameDAO.saveOrUpdate(uage);             //edit this substitute as ordinary player
                    mailService.sendMsgChangedToActor(newUser, game);
                }
            }
        }
    }

    public void notifyByMail(UserAttendedGame userAttendedGame) {
        //To change body of created methods use File | Settings | File Templates.
        if(userAttendedGame.getAttendedGame().getMailProhibition()) {
            return;
        }
        if(!userAttendedGame.isSubstitute()){
            mailService.sendMsgSignedAsRegular(userAttendedGame);
        } else {
            mailService.sendMsgSignedAsReplacement(userAttendedGame.getUserAttended(), userAttendedGame.getAttendedGame());
        }
    }

    public void notifyChangedByMail(UserAttendedGame userAttendedGame) {
        if(!userAttendedGame.isSubstitute()) {
            mailService.sendMsgChangedToActor(userAttendedGame.getUserAttended(), userAttendedGame.getAttendedGame());
            mailService.sendMsgToEditors(userAttendedGame.getUserAttended(), userAttendedGame.getAttendedGame());
        } else {
            mailService.sendMsgChangedToReplacement(userAttendedGame.getUserAttended(), userAttendedGame.getAttendedGame());
        }
    }
}
