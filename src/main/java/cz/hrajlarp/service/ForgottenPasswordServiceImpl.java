package cz.hrajlarp.service;

import cz.hrajlarp.model.dao.EmailAuthenticationDao;
import cz.hrajlarp.model.entity.EmailAuthenticatitonEntity;
import cz.hrajlarp.model.entity.HrajUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ForgottenPasswordServiceImpl implements ForgottenPasswordService {
    @Autowired
    private EmailAuthenticationDao emailAuthenticationDao;

    @Override
    public boolean authenticateUser(String authenticationLink) {
        try {
            emailAuthenticationDao.getByActivationLink(authenticationLink);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public HrajUserEntity getAuthenticatedUserByLink(String authenticationLink) {
        EmailAuthenticatitonEntity authentication =
                emailAuthenticationDao.getByActivationLink(authenticationLink);
        return authentication.getUser();
    }

    @Override
    public void updatePasswordForUserByLink(String mailLink) {

    }

    @Override
    public boolean isValidLink(String mailLink) {
        return false;
    }

    @Override
    public boolean generateNewLinkForUserAndSend(String email) {
        return false;
    }
}
