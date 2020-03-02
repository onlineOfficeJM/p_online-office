package ru.javamentor.onlineoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.javamentor.onlineoffice.entity.User;
import ru.javamentor.onlineoffice.service.GuestAccessService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
public class GuestAccessController {

    @Autowired
    private GuestAccessService guestAccessService;

    @GetMapping(value = "/admin/guestUrl/user/list")
    public String listUsersUrl(HttpServletRequest request, Principal principal) {
        int servletPathLength = request.getServletPath() == null ? 0 : request.getServletPath().length();
        int pathInfoLength = request.getPathInfo() == null ? 0 : request.getPathInfo().length();
        int length = servletPathLength + pathInfoLength;
        StringBuffer url = request.getRequestURL();
        url.setLength(url.length()-length);
        String baseUrl = url.toString();
        return guestAccessService.listUsersUrl(baseUrl);
    }

    @GetMapping("/guest/user/list/{code:^\\d{40}$}")
    public List<User> listUsers(@PathVariable String code, HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        return guestAccessService.listUsers(url);
    }
}
