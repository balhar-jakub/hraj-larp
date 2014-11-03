package cz.hrajlarp.service;

import cz.hrajlarp.model.entity.HrajUserEntity;

/**
 * Created by jbalhar on 5/30/2014.
 * This service serves for purposes of allowing user to reset their password via email sent.
 */
public interface ForgottenPasswordService {
    /**
     * It tests, whether the received link pairs with one user. If there is any other amount
     * than one user, it pairs with.
     *
     * @param authenticationLink Link user used to authenticate
     * @return True if there is any user associated with this link.
     */
    public boolean isValidLink(String authenticationLink);

    /**
     * This generates and saves new forgotten email link for given user. Email for user must be valid.
     *
     * @param email User who asked for password. If this email doesn't exist throws NonExistentUserException
     * @return Generated email.
     */
    public boolean generateNewLinkForUserAndSend(String email);

    /**
     * It returns user authenticated by the link. Do not use this if you are not sure
     * if there is user associated with this link. Then use authenticateUser method.
     *
     * @param authenticationLink Link representing user.
     * @return User belonging to this link.
     */
    public HrajUserEntity getAuthenticatedUserByLink(String authenticationLink);

    void updatePasswordForUserById(int actualUser, String password);
}
