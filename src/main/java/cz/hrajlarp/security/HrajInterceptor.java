package cz.hrajlarp.security;

import cz.hrajlarp.entity.HrajUser;
import cz.hrajlarp.service.RightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class HrajInterceptor implements HandlerInterceptor {
    @Autowired
    private RightsService rightsService;

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
        HrajUser hrajUser = rightsService.getLoggedUser();
        Boolean hasRights = rightsService.isEditor(hrajUser) || rightsService.isAdministrator(hrajUser);
        if(model != null) {
            model.addObject("hasRights", hasRights);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
