package cz.hrajlarp.controller;

import cz.hrajlarp.dto.EmailAuthenticationDto;
import cz.hrajlarp.service.ForgottenPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * This password takes care of forgotten password.
 */
@Controller
public class ForgottenPasswordController {
    @Autowired
    private ForgottenPasswordService forgottenPasswordService;

    /**
     * This one is called from a form. If given email belongs to some user,
     * this correctly sends password. Otherwise it returns error.
     * @param email
     */
    @RequestMapping(value="/user/forgotten/", method = RequestMethod.POST)
    public String sendEmailWithGeneratedLink(
            @RequestBody @Valid EmailAuthenticationDto email
    ) {
        if(forgottenPasswordService.generateNewLinkForUserAndSend(email.getEmail())) {
            return "user/forgotten/send";
        } else {
            return "user/forgotten/";
        }
    }

    /**
     * This one simply shows field for email. If there is user with given password
     * tell the user that it was sent. Otherwise tell him about the error.
     */
    @RequestMapping(value="/user/forgotten/", method = RequestMethod.GET)
    public String askForNewPassword() {
        return "user/forgotten/mail";
    }

    /**
     * It updates password for given user.
     *
     * @param mailLink
     * @return
     */
    @RequestMapping(value="/user/password/new/{mailLink}", method = RequestMethod.POST)
    public String updatePassword(
            @PathVariable String mailLink
    ) {
        forgottenPasswordService.updatePasswordForUserByLink(mailLink);
        // Ideal case is when in this moment we will login person in.
        return "user/forgotten/updated";
    }

    @RequestMapping(value="/user/password/new/{mailLink}", method = RequestMethod.GET)
    public String showUpdatePassword(
            @PathVariable String mailLink
    ) {
        if(forgottenPasswordService.isValidLink(mailLink)) {
            return "";
        } else {
            return "";
        }
    }
}