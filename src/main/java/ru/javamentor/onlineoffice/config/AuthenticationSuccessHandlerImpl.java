package ru.javamentor.onlineoffice.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.javamentor.onlineoffice.entity.ActiveUserStore;
import ru.javamentor.onlineoffice.entity.LoggedUser;
import ru.javamentor.onlineoffice.events.ActiveUserStoreUpdateEventPublisher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    private final ActiveUserStore activeUserStore;
    private final ActiveUserStoreUpdateEventPublisher publisher;

    public AuthenticationSuccessHandlerImpl(ActiveUserStore activeUserStore, ActiveUserStoreUpdateEventPublisher publisher) {
        this.activeUserStore = activeUserStore;
        this.publisher = publisher;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            LoggedUser user = new LoggedUser(authentication.getName(), activeUserStore);
            session.setAttribute("user", user);
            publisher.publishUpdateEvent("User " + user.getUsername() + " logged in");
        }

        response.sendRedirect("/");
    }
}

