package cz.hrajlarp.service;

import cz.hrajlarp.model.dao.EmailAuthenticationDao;
import cz.hrajlarp.model.dao.UserDAO;
import cz.hrajlarp.model.entity.EmailAuthenticatitonEntity;
import cz.hrajlarp.model.entity.HrajUserEntity;
import cz.hrajlarp.utils.HashString;
import cz.hrajlarp.utils.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ForgottenPasswordServiceImpl implements ForgottenPasswordService {
    @Autowired
    private EmailAuthenticationDao emailAuthenticationDao;
    @Autowired
    private UserDAO userDao;
    @Autowired
    private MailService mailService;

    @Override
    public HrajUserEntity getAuthenticatedUserByLink(String authenticationLink) {
        EmailAuthenticatitonEntity authentication =
                emailAuthenticationDao.getByActivationLink(authenticationLink);
        return authentication.getUser();
    }

    @Override
    public boolean isValidLink(String authenticationLink) {
        try {
            emailAuthenticationDao.getByActivationLink(authenticationLink);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void updatePasswordForUserById(int actualUser, String password) {
        HrajUserEntity user = userDao.getUserById(actualUser);
        try {
            user.setPassword(new HashString().digest(password));
            userDao.editUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public boolean generateNewLinkForUserAndSend(String userName) {
        try {
            System.out.println("UserName" + userName);
            HrajUserEntity user = userDao.getUserByLogin(userName);
            String link = new HashString().digest(UUID.randomUUID().toString());
            mailService.sendForgottenPassword(user.getEmail(), link);
            EmailAuthenticatitonEntity toSave = new EmailAuthenticatitonEntity();
            toSave.setAuth_token(link);
            toSave.setUser(user);
            toSave.setUser_id(user.getId());
            emailAuthenticationDao.saveOrUpdate(toSave);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
