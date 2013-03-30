package cz.hrajlarp.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by IntelliJ IDEA.
 * User: Prasek
 * Date: 24.3.13
 * Time: 15:17
 * To change this template use File | Settings | File Templates.
 */
public class Rights {

    public static boolean isLogged(Authentication a){
        // TODO add check for anonymous user!
        return (a != null && a.getPrincipal() != null && a.isAuthenticated()
                && a.getPrincipal() instanceof UserDetails);
    }
}
