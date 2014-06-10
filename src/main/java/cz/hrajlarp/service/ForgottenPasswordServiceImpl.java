package cz.hrajlarp.service;

import cz.hrajlarp.model.dao.EmailAuthenticationDao;
import cz.hrajlarp.model.entity.EmailAuthenticatitonEntity;
import cz.hrajlarp.model.entity.HrajUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jbalhar on 5/30/2014.
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
    public boolean generateNewLinkForUserAndSend(String email) {
        return null;
    }
}
