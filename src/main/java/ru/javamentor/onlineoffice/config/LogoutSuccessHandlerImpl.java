package ru.javamentor.onlineoffice.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import ru.javamentor.onlineoffice.events.ActiveUserStoreUpdateEventPublisher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component("LogoutSuccessHandlerImpl")
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    private final ActiveUserStoreUpdateEventPublisher publisher;

    public LogoutSuccessHandlerImpl(ActiveUserStoreUpdateEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session != null){
            session.removeAttribute("user");
            publisher.publishUpdateEvent("User " + authentication.getName() + " logged out");
        }
        response.sendRedirect( "/");
    }
}
