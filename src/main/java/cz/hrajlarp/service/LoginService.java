package cz.hrajlarp.service;

import cz.hrajlarp.model.entity.HrajUserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Created by jbalhar on 5/30/2014.
 */
@Service
public interface LoginService {
    HrajUserEntity loadUserByUsername(String username);
}
