package ru.javamentor.onlineoffice.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.javamentor.onlineoffice.entity.ActiveUserStore;
import ru.javamentor.onlineoffice.entity.LoggedUser;
import ru.javamentor.onlineoffice.entity.User;
import ru.javamentor.onlineoffice.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final ActiveUserStore activeUserStore;
    private final UserService userService;

    public AuthenticationSuccessHandlerImpl(ActiveUserStore activeUserStore, UserService userService) {
        this.activeUserStore = activeUserStore;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            LoggedUser user = new LoggedUser((User)userService.loadUserByUsername(authentication.getName()), activeUserStore);
            session.setAttribute("user", user);
        }
        response.sendRedirect(request.getContextPath() + "/");
    }
}

