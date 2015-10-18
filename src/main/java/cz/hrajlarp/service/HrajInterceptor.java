package cz.hrajlarp.service;

import cz.hrajlarp.model.entity.HrajUserEntity;
import cz.hrajlarp.model.Rights;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Jakub Balhar
 * Date: 15.4.13
 * Time: 11:50
 */
@Service
public class HrajInterceptor implements HandlerInterceptor {
    @Autowired
    private Rights rights;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object obj,
            ModelAndView model
    ) {
        HrajUserEntity hrajUser = rights.getLoggedUser();
        Boolean hasRights = rights.isEditor(hrajUser) || rights.isAdministrator(hrajUser);
        if(model != null) {
            model.addObject("hasRights", hasRights);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
