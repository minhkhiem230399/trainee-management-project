package com.edu.hutech.config;

import com.edu.hutech.entities.User;
import com.edu.hutech.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Autowired
    UserRepository repository;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User user = null;
        if(authentication.getPrincipal() instanceof Principal) {
            user = (User) authentication.getPrincipal();

        }else {
            user = ((User)authentication.getPrincipal());
        }
        HttpSession session = httpServletRequest.getSession();
        if(!user.getRoles().getAuthority().equals("ROLE_ADMIN")){
            session.setAttribute("userId", user.getId());
            session.setAttribute("role", user.getRoles().getAuthority());
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/class-management");
        }else {
            session.setAttribute("userId", null);
            session.setAttribute("role", null);
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/dashboard");
        }
    }
}
