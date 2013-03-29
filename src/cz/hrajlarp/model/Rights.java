package cz.hrajlarp.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

/**
 * Created by IntelliJ IDEA.
 * User: Prasek
 * Date: 24.3.13
 * Time: 15:17
 * To change this template use File | Settings | File Templates.
 */
public class Rights {

    public boolean isLogged(Authentication a){
        return (a.isAuthenticated());
    }
}
